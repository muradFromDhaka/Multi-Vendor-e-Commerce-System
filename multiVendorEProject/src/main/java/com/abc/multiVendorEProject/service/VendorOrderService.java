package com.abc.multiVendorEProject.service;//package com.abc.SpringSecurityExample.service;
//
//
//import com.abc.SpringSecurityExample.Config.ResourceNotFoundException;
//import com.abc.SpringSecurityExample.DTOs.projectDtos.vendorDto.VendorOrderDetailsResponseDto;
//import com.abc.SpringSecurityExample.DTOs.projectDtos.vendorDto.VendorOrderListResponseDto;
//import com.abc.SpringSecurityExample.entity.Vendor;
//import com.abc.SpringSecurityExample.entity.VendorOrder;
//import com.abc.SpringSecurityExample.enums.VendorOrderStatus;
//import com.abc.SpringSecurityExample.mapper.VendorOrderMapper;
//import com.abc.SpringSecurityExample.repository.VendorOrderRepository;
//import com.abc.SpringSecurityExample.repository.VendorRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//
//@Service
//@RequiredArgsConstructor
//@Transactional()
//public class VendorOrderService {
//
//    private final VendorOrderRepository vendorOrderRepository;
//    private final VendorRepository vendorRepository;
//
////=========================Helper Methods==========================
//
//    private Vendor getLoggedInVendor() {
//
//        String username = SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getName();
//
//        return vendorRepository.findByUserUserName(username)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("Vendor not found"));
//    }
//
//
//    private VendorOrder getVendorOrder(Long id) {
//
//        Vendor vendor = getLoggedInVendor();
//
//        return vendorOrderRepository
//                .findByIdAndVendor(id, vendor)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("Vendor Order not found"));
//    }
//
////    ===================Orginal method============================
//
//    public Page<VendorOrderListResponseDto> getMyOrders(
//            Pageable pageable) {
//
//        Vendor vendor = getLoggedInVendor();
//
//        return vendorOrderRepository
//                .findByVendor(vendor, pageable)
//                .map(VendorOrderMapper::toListDto);
//    }
//
//    public Page<VendorOrderListResponseDto> getMyOrdersByStatus(
//            VendorOrderStatus status,
//            Pageable pageable) {
//
//        Vendor vendor = getLoggedInVendor();
//
//        return vendorOrderRepository
//                .findByVendorAndStatus(vendor, status, pageable)
//                .map(VendorOrderMapper::toListDto);
//    }
//
//    public VendorOrderDetailsResponseDto getOrderDetails(
//            Long vendorOrderId) {
//
//        VendorOrder vendorOrder = getVendorOrder(vendorOrderId);
//
//        return VendorOrderMapper.toDetailsDto(vendorOrder);
//    }
//
//    @Transactional
//    public VendorOrderDetailsResponseDto updateStatus(
//            Long vendorOrderId,
//            VendorOrderStatus status) {
//
//        VendorOrder vendorOrder = getVendorOrder(vendorOrderId);
//
//        // Business validation
//        if (vendorOrder.getStatus() == VendorOrderStatus.DELIVERED) {
//            throw new RuntimeException("Delivered order cannot be updated.");
//        }
//
//        vendorOrder.setStatus(status);
//
//        return VendorOrderMapper.toDetailsDto(vendorOrder);
//    }
//
//}