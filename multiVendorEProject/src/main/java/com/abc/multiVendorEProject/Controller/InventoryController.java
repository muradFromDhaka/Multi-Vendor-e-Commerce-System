package com.abc.multiVendorEProject.Controller;//package com.abc.SpringSecurityExample.Controller;
//
//import com.abc.SpringSecurityExample.DTOs.projectDtos.InventoryRequestDto;
//import com.abc.SpringSecurityExample.DTOs.projectDtos.InventoryResponseDto;
//import com.abc.SpringSecurityExample.service.InventoryService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/inventories")
//@RequiredArgsConstructor
//public class InventoryController {
//    private final InventoryService inventoryService;
//
//
//    @GetMapping
//    public ResponseEntity<List<InventoryResponseDto>> getAll(){
//        List<InventoryResponseDto> list = inventoryService.getAllInventory();
//        return ResponseEntity.ok(list);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<InventoryResponseDto> getById(@PathVariable Long id){
//        InventoryResponseDto inven = inventoryService.getInventoryId(id);
//        return ResponseEntity.ok(inven);
//    }
//
//    @PostMapping
//    public ResponseEntity<String> create(@RequestBody InventoryRequestDto dto){
//        String createInventory = inventoryService.createInventory(dto);
//
//        return ResponseEntity.status(201).body(createInventory);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody InventoryRequestDto dto){
//        String updateInventory = inventoryService.updateInventory(id, dto);
//
//        return ResponseEntity.ok("Inventory updated successfully.");
//    }
//
//    @DeleteMapping("{id}")
//    public ResponseEntity<String> delete(@PathVariable Long id){
//        inventoryService.deleteInventory(id);
//        return ResponseEntity.ok("Inventory deleted successfully.");
//    }
//
//}
