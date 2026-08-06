package com.abc.multiVendorEProject.service.Vendor;

import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.vendorOrderDto.UpdateVendorOrderStatusRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.vendorOrderDto.VendorOrderDetailsResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.vendorOrderDto.VendorOrderListResponseDto;
import com.abc.multiVendorEProject.entity.OrderItem;
import com.abc.multiVendorEProject.entity.Product;
import com.abc.multiVendorEProject.entity.Vendor;
import com.abc.multiVendorEProject.entity.VendorOrder;
import com.abc.multiVendorEProject.enums.VendorOrderStatus;
import com.abc.multiVendorEProject.mapper.VendorOrderMapper;
import com.abc.multiVendorEProject.repository.OrderItemRepository;
import com.abc.multiVendorEProject.repository.ProductRepository;
import com.abc.multiVendorEProject.repository.VendorOrderRepository;
import com.abc.multiVendorEProject.repository.VendorRepository;
import com.abc.multiVendorEProject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Transactional
@Service
public class VendorOrderService {


    private final VendorOrderRepository vendorOrderRepository;
    private final VendorRepository vendorRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderService orderService;
    private final ProductRepository productRepository;


    @Transactional(readOnly = true)
    public Page<VendorOrderListResponseDto> getVendorOrders(Pageable pageable) {

        Vendor vendor = getLoggedInVendor();

        return vendorOrderRepository
                .findByVendorOrderByCreatedAtDesc(vendor, pageable)
                .map(VendorOrderMapper::toListDto);
    }


    @Transactional(readOnly = true)
    public VendorOrderDetailsResponseDto getVendorOrderDetails(Long vendorOrderId) {

        Vendor vendor = getLoggedInVendor();

        VendorOrder vendorOrder = vendorOrderRepository
                .findByIdAndVendor(vendorOrderId, vendor)
                .orElseThrow(() ->
                        new RuntimeException("Vendor order not found."));

        return VendorOrderMapper.toDetailsDto(vendorOrder);
    }

    // ===============================
// Vendor Dashboard
// ===============================

    @Transactional(readOnly = true)
    public BigDecimal getVendorRevenue() {

        Vendor vendor = getLoggedInVendor();

        return orderItemRepository.getVendorRevenue(vendor.getId());
    }


    @Transactional(readOnly = true)
    public long getVendorCustomers() {

        Vendor vendor = getLoggedInVendor();

        return orderItemRepository.countCustomers(vendor.getId());
    }

    @Transactional(readOnly = true)
    public Long getVendorProductsSold() {

        Vendor vendor = getLoggedInVendor();

        return orderItemRepository.getTotalProductsSold(vendor.getId());
    }


    // ===============================
// Update Vendor Order Status
// ===============================

    @Transactional
    public VendorOrderDetailsResponseDto updateVendorOrderStatus(
            Long vendorOrderId,
            UpdateVendorOrderStatusRequestDto request) {

        Vendor vendor = getLoggedInVendor();

        VendorOrder vendorOrder = vendorOrderRepository
                .findByIdAndVendor(vendorOrderId, vendor)
                .orElseThrow(() ->
                        new RuntimeException("Vendor order not found."));

        validateVendorOrderStatusTransition(
                vendorOrder.getVendorOrderStatus(),
                request.vendorOrderStatus());

        vendorOrder.setVendorOrderStatus(request.vendorOrderStatus());

        try {

            vendorOrderRepository.save(vendorOrder);

            if (request.vendorOrderStatus() == VendorOrderStatus.DELIVERED) {
                updateSoldCount(vendorOrder, SoldCountAction.INCREASE);
            }

            if (request.vendorOrderStatus() == VendorOrderStatus.RETURNED) {
                updateSoldCount(vendorOrder, SoldCountAction.DECREASE);
            }

            orderService.updateParentOrderStatus(
                    vendorOrder.getOrder().getId());

        } catch (ObjectOptimisticLockingFailureException ex) {

            throw new RuntimeException(
                    "This order was updated by another request.");
        }

        return VendorOrderMapper.toDetailsDto(vendorOrder);
    }

    // ===============================
// Helper
// ===============================

    public Vendor getLoggedInVendor() {

        String userName = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return vendorRepository.findByUserUserName(userName)
                .orElseThrow(() ->
                        new RuntimeException("Vendor not found."));
    }

    private enum SoldCountAction {
        INCREASE,
        DECREASE
    }

    private void updateSoldCount(
            VendorOrder vendorOrder,
            SoldCountAction action
    ) {

        Map<Long, Product> products = new HashMap<>();

        for (OrderItem item : vendorOrder.getOrderItems()) {

            Product product = item.getVariant().getProduct();

            if (action == SoldCountAction.INCREASE) {
                product.setSoldCount(product.getSoldCount() + item.getQuantity());
            } else {
                product.setSoldCount(
                        Math.max(0, product.getSoldCount() - item.getQuantity())
                );
            }

            products.put(product.getId(), product);
        }

        productRepository.saveAll(products.values());
    }


    private void validateVendorOrderStatusTransition(
            VendorOrderStatus currentVendorOrderStatus,
            VendorOrderStatus newVendorOrderStatus) {

//        if (currentVendorOrderStatus == VendorOrderStatus.DELIVERED) {
//            throw new RuntimeException(
//                    "Delivered vendor order cannot be updated.");
//        }

        if (currentVendorOrderStatus == VendorOrderStatus.CANCELLED) {
            throw new RuntimeException(
                    "Cancelled vendor order cannot be updated.");
        }

        if (currentVendorOrderStatus == newVendorOrderStatus) {
            throw new RuntimeException(
                    "Vendor order is already in " + newVendorOrderStatus + " status.");
        }

        switch (currentVendorOrderStatus) {

            case PENDING -> {
                if (newVendorOrderStatus != VendorOrderStatus.CONFIRMED
                        && newVendorOrderStatus != VendorOrderStatus.CANCELLED) {
                    throw new RuntimeException(
                            "Pending order can only be CONFIRMED or CANCELLED.");
                }
            }

            case CONFIRMED -> {
                if (newVendorOrderStatus != VendorOrderStatus.PROCESSING
                        && newVendorOrderStatus != VendorOrderStatus.CANCELLED) {
                    throw new RuntimeException(
                            "Confirmed order can only be PROCESSING or CANCELLED.");
                }
            }

            case PROCESSING -> {
                if (newVendorOrderStatus != VendorOrderStatus.PACKED) {
                    throw new RuntimeException(
                            "Processing order can only be PACKED.");
                }
            }

            case PACKED -> {

                if (newVendorOrderStatus != VendorOrderStatus.SHIPPED) {
                    throw new RuntimeException(
                            "PACKED order can only be SHIPPED.");
                }

            }

            case SHIPPED -> {
                if (newVendorOrderStatus != VendorOrderStatus.DELIVERED) {
                    throw new RuntimeException(
                            "Shipped order can only be DELIVERED.");
                }
            }

            case DELIVERED -> {
                if (newVendorOrderStatus != VendorOrderStatus.RETURNED) {
                    throw new RuntimeException(
                            "Delivered order can only be RETURNED.");
                }
            }

            case RETURNED -> {
                throw new RuntimeException(
                        "Returned order cannot be updated.");
               }

        }
    }

}
