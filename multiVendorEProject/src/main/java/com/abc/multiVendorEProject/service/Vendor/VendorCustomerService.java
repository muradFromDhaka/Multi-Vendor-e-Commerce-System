package com.abc.multiVendorEProject.service.Vendor;

import com.abc.multiVendorEProject.DTOs.projectDtos.Vendor.Customer.VendorCustomerDetailsResponseDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.Vendor.Customer.VendorCustomerOrderItemResponseDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.Vendor.Customer.VendorCustomerOrderResponseDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.Vendor.Customer.VendorCustomerResponseDTO;
import com.abc.multiVendorEProject.entity.*;
import com.abc.multiVendorEProject.mapper.Vendor.VendorCustomerMapper;
import com.abc.multiVendorEProject.repository.OrderRepository;
import com.abc.multiVendorEProject.repository.UserRepository;
import com.abc.multiVendorEProject.repository.VendorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class VendorCustomerService {

    private final VendorRepository vendorRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    // ============================
    // Public APIs
    // ============================

    @Transactional(readOnly = true)
    public Page<VendorCustomerResponseDTO> getAllCustomers(
            Pageable pageable) {

        Vendor currentVendor = getCurrentVendor();

        System.out.println("Vendor Id ============================== " + currentVendor.getId());

        return orderRepository.findCustomerSummaries(
                currentVendor.getId(),
                pageable
        );
    }

    @Transactional(readOnly = true)
    public VendorCustomerDetailsResponseDTO getCustomerDetails(
            String userName) {

        Vendor currentVendor = getCurrentVendor();

        User customer = userRepository.findById(userName)
                .orElseThrow(() ->
                        new EntityNotFoundException("Customer not found."));

        List<Order> orders =
                orderRepository.findByUserUserName(userName);

        List<VendorCustomerOrderResponseDTO> orderResponses =
                buildOrderResponses(orders, currentVendor);

        if (orderResponses.isEmpty()) {
            throw new EntityNotFoundException(
                    "This customer has not ordered any product from your shop.");
        }

        BigDecimal totalSpent = orderResponses.stream()
                .map(VendorCustomerOrderResponseDTO::vendorTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        LocalDateTime lastOrderDate = orderResponses.stream()
                .map(VendorCustomerOrderResponseDTO::orderDate)
                .max(LocalDateTime::compareTo)
                .orElse(null);

        String phone = orders.stream()
                .map(Order::getShippingAddress)
                .filter(Objects::nonNull)
                .map(ShippingAddress::getPhone)
                .findFirst()
                .orElse(null);

        return VendorCustomerMapper.toCustomerDetailsResponse(
                customer,
                phone,
                (long) orderResponses.size(),
                totalSpent,
                lastOrderDate,
                orderResponses
        );
    }


    // ============================
    // Helper Methods
    // ============================

    private Vendor getCurrentVendor() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String userName = authentication.getName();

        return vendorRepository
                .findByUserUserName(userName)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Vendor account not found."
                        ));
    }

    private List<VendorCustomerOrderResponseDTO> buildOrderResponses(
            List<Order> orders,
            Vendor currentVendor) {

        List<VendorCustomerOrderResponseDTO> responses =
                new ArrayList<>();

        for (Order order : orders) {

            List<OrderItem> vendorItems = order.getOrderItems()
                    .stream()
                    .filter(item ->
                            item.getVendor().getId().equals(currentVendor.getId()))
                    .toList();

            if (vendorItems.isEmpty()) {
                continue;
            }

            List<VendorCustomerOrderItemResponseDTO> itemResponses =
                    buildOrderItems(vendorItems);

            BigDecimal vendorTotalPrice =
                    calculateVendorTotalPrice(vendorItems);

            responses.add(
                    VendorCustomerMapper.toOrderResponse(
                            order,
                            vendorTotalPrice,
                            itemResponses
                    )
            );
        }

        return responses;
    }

    private List<VendorCustomerOrderItemResponseDTO> buildOrderItems(
            List<OrderItem> items) {

        return items.stream()
                .map(VendorCustomerMapper::toOrderItemResponse)
                .toList();
    }

    private BigDecimal calculateVendorTotalPrice(
            List<OrderItem> items) {

        return items.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}