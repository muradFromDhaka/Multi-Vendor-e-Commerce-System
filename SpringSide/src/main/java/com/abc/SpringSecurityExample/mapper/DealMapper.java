package com.abc.SpringSecurityExample.mapper;

import com.abc.SpringSecurityExample.DTOs.projectDtos.DealRequestDto;
import com.abc.SpringSecurityExample.DTOs.projectDtos.DealResponseDto;
import com.abc.SpringSecurityExample.entity.Deal;
import com.abc.SpringSecurityExample.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class DealMapper {

    public Deal toEntity(DealRequestDto dto){
        Deal deal = new Deal();
        deal.setTitle(dto.title());
        deal.setDiscountPercent(dto.discountPercent());
        deal.setStartTime(dto.startTime());
        deal.setEndTime(dto.endTime());
        return deal;
    }


    public DealResponseDto toDto(Deal deal) {

        if (deal.getProduct() == null) {
            return new DealResponseDto(
                    deal.getId(),
                    deal.getTitle(),
                    deal.getDiscountPercent(),
                    deal.getStartTime(),
                    deal.getEndTime(),
                    null,
                    null
            );
        } else {
            return new DealResponseDto(
                    deal.getId(),
                    deal.getTitle(),
                    deal.getDiscountPercent(),
                    deal.getStartTime(),
                    deal.getEndTime(),
                    deal.getProduct().getId(),
                    deal.getProduct().getName()
            );
        }
    }


    public void updateEntity(Deal deal, DealRequestDto dto) {
        deal.setTitle(dto.title());
        deal.setDiscountPercent(dto.discountPercent());
        deal.setStartTime(dto.startTime());
        deal.setEndTime(dto.endTime());

    }
}
