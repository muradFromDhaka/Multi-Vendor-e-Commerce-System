package com.abc.multiVendorEProject.mapper;

import com.abc.multiVendorEProject.DTOs.projectDtos.DealRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.DealResponseDto;
import com.abc.multiVendorEProject.entity.Deal;
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

        return new DealResponseDto(

                deal.getId(),

                deal.getTitle(),

                deal.getDiscountPercent(),

                deal.getActive(),

                deal.getStartTime(),

                deal.getEndTime(),

                deal.getProduct() != null
                        ? deal.getProduct().getId()
                        : null,

                deal.getProduct() != null
                        ? deal.getProduct().getName()
                        : null
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