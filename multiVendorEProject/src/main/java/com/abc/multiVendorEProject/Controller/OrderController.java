package com.abc.multiVendorEProject.Controller;

import com.abc.multiVendorEProject.DTOs.projectDtos.OrderDto.OrderResponseDto;
import com.abc.multiVendorEProject.enums.OrderStatus;
import com.abc.multiVendorEProject.service.Customer.InvoiceService;
import com.abc.multiVendorEProject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final InvoiceService invoiceService;

    @GetMapping
    public Page<OrderResponseDto> getMyOrders(
            Pageable pageable) {

        return orderService.getMyOrders(pageable);
    }

    @GetMapping("/status")
    public Page<OrderResponseDto> getMyOrdersByStatus(
            @RequestParam OrderStatus status,
            Pageable pageable) {

        return orderService.getMyOrdersByStatus(status, pageable);
    }

    @GetMapping("/{orderId}")
    public OrderResponseDto getMyOrder(
            @PathVariable Long orderId) {

        System.out.println("Controller : " + orderId);

        return orderService.getMyOrder(orderId);
    }

    @PatchMapping("/{orderId}/cancel")
    public OrderResponseDto cancelMyOrder(
            @PathVariable Long orderId) {

        return orderService.cancelMyOrder(orderId);
    }


    @GetMapping("/purchased/{productId}")
    public boolean hasPurchasedProduct(
            @PathVariable Long productId) {

        return orderService.hasPurchasedProduct(productId);
    }


    @GetMapping("/invoice/{orderId}")
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable Long orderId) {

        byte[] pdf = invoiceService.generateInvoice(orderId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Invoice-" + orderId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}