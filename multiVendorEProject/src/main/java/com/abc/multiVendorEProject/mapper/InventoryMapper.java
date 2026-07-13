package com.abc.multiVendorEProject.mapper;//package com.abc.SpringSecurityExample.mapper;
//
//import com.abc.SpringSecurityExample.DTOs.projectDtos.InventoryRequestDto;
//import com.abc.SpringSecurityExample.DTOs.projectDtos.InventoryResponseDto;
//import com.abc.SpringSecurityExample.entity.Inventory;
//import com.abc.SpringSecurityExample.entity.Product;
//import org.springframework.stereotype.Component;
//
//@Component
//public class InventoryMapper {
//
//    public Inventory toEntity(InventoryRequestDto dto, Inventory in){
//       if(dto == null) return null;
//       in.setInventoryName(dto.getInventoryName());
//       in.setAvailableQuantity(dto.getAvailableQuantity());
//       in.setReservedQuantity(dto.getReservedQuantity());
//       if(dto.getProductId() != null){
//           Product product = new Product();
//           product.setId(dto.getProductId());
//           in.setProduct(product);
//       }
//
//       return in;
//    }
//
//    public InventoryResponseDto toDto(Inventory in, InventoryResponseDto dto){
//         if(in == null) return  null;
//        dto.setId(in.getId());
//        dto.setInventoryName(in.getInventoryName());
//        dto.setAvailableQuantity(in.getAvailableQuantity());
//        dto.setReservedQuantity(in.getReservedQuantity());
//        if(in.getProduct() != null){
//            dto.setProductId(in.getProduct().getId());
//        }
//
//        return dto;
//    }
//}
