package com.abc.multiVendorEProject.Controller;//package com.abc.multiVendorEProject.Controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/vendor-order")
//@RequiredArgsConstructor
//public class VendorOrderController {
//
//    private final VendorOrderService vendorOrderService;
//
//    @GetMapping
//    public ResponseEntity<List<VendorOrderResponseDto>> getAll(){
//        List<VendorOrderResponseDto> dto = vendorOrderService.getAll();
//
//        return ResponseEntity.ok(dto);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<VendorOrderResponseDto> getById(@PathVariable Long id){
//
//            VendorOrderResponseDto dto = vendorOrderService.getById(id);
//            return ResponseEntity.ok(dto);
//    }
//
//    @PostMapping
//    public ResponseEntity<VendorOrderResponseDto> create(@RequestBody VendorOrderRequestDto dto){
//        VendorOrderResponseDto entity = vendorOrderService.create(dto);
//        return ResponseEntity.status(201).body(entity);
//    }
//
//    @PutMapping("/{id}/status")
//    public ResponseEntity<VendorOrderResponseDto> update(
//            @PathVariable Long id,
//            @RequestParam VendorOrderStatus status){
//        VendorOrderResponseDto responseDto = vendorOrderService.updateStatus(id,status);
//
//        return  ResponseEntity.ok(responseDto);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id){
//        vendorOrderService.delete(id);
//
//        return ResponseEntity.noContent().build();
//    }
//
//
//    @GetMapping("/vendor/{vendorId}")
//    public ResponseEntity<List<VendorOrderResponseDto>>
//    getByVendorId(@PathVariable Long vendorId) {
//
//        return ResponseEntity.ok(
//                vendorOrderService.getByVendorId(vendorId));
//    }
//
//    @GetMapping("/order/{orderId}")
//    public ResponseEntity<List<VendorOrderResponseDto>>
//    getByOrderId(@PathVariable Long orderId) {
//
//        return ResponseEntity.ok(
//                vendorOrderService.getByOrderId(orderId));
//    }
//
//    @GetMapping("/vendor/{vendorId}/status")
//    public ResponseEntity<List<VendorOrderResponseDto>>
//    getByVendorAndStatus(
//            @PathVariable Long vendorId,
//            @RequestParam VendorOrderStatus status) {
//
//        return ResponseEntity.ok(
//                vendorOrderService.getByVendorAndStatus(
//                        vendorId,
//                        status));
//    }
//
//
//    @GetMapping("/vendor/{vendorId}/count")
//    public ResponseEntity<Long>
//    getVendorOrderCount(
//            @PathVariable Long vendorId) {
//
//        return ResponseEntity.ok(
//                vendorOrderService.getVendorOrderCount(
//                        vendorId));
//    }
//
//
//    @GetMapping("/vendor/{vendorId}/count/status")
//    public ResponseEntity<Long>
//    getVendorOrderCountByStatus(
//            @PathVariable Long vendorId,
//            @RequestParam VendorOrderStatus status) {
//
//        return ResponseEntity.ok(
//                vendorOrderService
//                        .getVendorOrderCountByStatus(
//                                vendorId,
//                                status));
//    }
//
//
//    @GetMapping("/vendor/{vendorId}/revenue")
//    public ResponseEntity<BigDecimal>
//    getVendorRevenue(
//            @PathVariable Long vendorId) {
//
//        return ResponseEntity.ok(
//                vendorOrderService.getVendorRevenue(
//                        vendorId));
//    }
//
//
//
//    @GetMapping("/vendor/{vendorId}/page")
//    public ResponseEntity<Page<VendorOrderResponseDto>>
//    getVendorOrders(
//            @PathVariable Long vendorId,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//
//        return ResponseEntity.ok(
//                vendorOrderService.getVendorOrders(
//                        vendorId,
//                        page,
//                        size));
//    }
//
//
//
//
//}
