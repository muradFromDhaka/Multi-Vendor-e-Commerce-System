package com.abc.multiVendorEProject.mapper;

import com.abc.multiVendorEProject.DTOs.projectDtos.DealRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.DealResponseDto;
import com.abc.multiVendorEProject.entity.Deal;
import com.abc.multiVendorEProject.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class DealMapper {

    // ==========================
    // Request DTO -> Entity
    // ==========================
    public Deal toEntity(DealRequestDto dto) {

        if (dto == null) {
            return null;
        }

        Deal deal = new Deal();

        deal.setTitle(dto.title());
        deal.setDiscountPercent(dto.discountPercent());
        deal.setStartTime(dto.startTime());
        deal.setEndTime(dto.endTime());

        // Default value
        deal.setActive(true);

        return deal;
    }

    // ==========================
    // Entity -> Response DTO
    // ==========================
    public DealResponseDto toDto(Deal deal) {

        if (deal == null) {
            return null;
        }

        Product product = deal.getProduct();

//        System.out.println("productName----------------------------"+ product.getName());
//        System.out.println("productImage----------------------------"+ product.getImageUrls());

        return new DealResponseDto(

                deal.getId(),

                deal.getTitle(),

                deal.getDiscountPercent(),

                deal.getActive(),

                deal.getStartTime(),

                deal.getEndTime(),

                product != null ? product.getId() : null,

                product != null
                        && product.getImageUrls() != null
                        && !product.getImageUrls().isEmpty()
                        ? product.getImageUrls().get(0)
                        : null,

                product != null ? product.getName() : null
        );
    }

    // ==========================
    // Update Entity
    // ==========================
    public void updateEntity(
            Deal deal,
            DealRequestDto dto
    ) {

        deal.setTitle(dto.title());
        deal.setDiscountPercent(dto.discountPercent());
        deal.setStartTime(dto.startTime());
        deal.setEndTime(dto.endTime());

        // Product update Service layer-এ করবেন
    }

}