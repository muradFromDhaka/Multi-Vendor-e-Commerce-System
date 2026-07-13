package com.abc.multiVendorEProject.service;//package com.abc.SpringSecurityExample.service;
//
//import com.abc.SpringSecurityExample.DTOs.projectDtos.InventoryRequestDto;
//import com.abc.SpringSecurityExample.DTOs.projectDtos.InventoryResponseDto;
//import com.abc.SpringSecurityExample.entity.Inventory;
//import com.abc.SpringSecurityExample.mapper.InventoryMapper;
//import com.abc.SpringSecurityExample.repository.InventoryRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class InventoryService {
//    private final InventoryRepository inventoryRepository;
//    private final InventoryMapper inventoryMapper;
//
//    public List<InventoryResponseDto> getAllInventory(){
//        List<InventoryResponseDto> allInventory = inventoryRepository.findAll().stream()
//                .map(inventory -> inventoryMapper.toDto(inventory, new InventoryResponseDto()))
//                .toList();
//
//        return allInventory;
//    }
//
//    public InventoryResponseDto getInventoryId(Long id){
//        InventoryResponseDto responseDto = inventoryRepository.findById(id)
//                .map(inventory -> inventoryMapper.toDto(inventory, new InventoryResponseDto()))
//                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id));
//
//        return responseDto;
//    }
//
//    public String createInventory(InventoryRequestDto dto){
//
//       Inventory in =  inventoryMapper.toEntity(dto, new Inventory());
//       return inventoryRepository.save(in).getInventoryName();
//    }
//
//    public String updateInventory(Long id, InventoryRequestDto dto){
//        Inventory existing = inventoryRepository.findById(id)
//                .orElseThrow(()-> new RuntimeException("Inventory not found with id: " + id));
//
//      Inventory update = inventoryMapper.toEntity(dto, existing);
//
//      return inventoryRepository.save(update).getInventoryName();
//    }
//
//    public void deleteInventory(Long id) {
//
//        Inventory in = inventoryRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id));
//
//        inventoryRepository.deleteById(in.getId());
//    }
//}
