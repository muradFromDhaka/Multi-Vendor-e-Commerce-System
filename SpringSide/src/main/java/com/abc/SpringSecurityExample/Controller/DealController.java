package com.abc.SpringSecurityExample.Controller;

import com.abc.SpringSecurityExample.DTOs.projectDtos.DealRequestDto;
import com.abc.SpringSecurityExample.DTOs.projectDtos.DealResponseDto;
import com.abc.SpringSecurityExample.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deals")
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;

    @GetMapping
    public Page<DealResponseDto> getAllDeals(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam String sortDir
    ){

        return dealService.getAllDeals(page,size,sortBy,sortDir);

    }

    @GetMapping("/{id}")
    public DealResponseDto getDealById(@PathVariable Long id){
        return dealService.getDealById(id);
    }

    @PostMapping
    public ResponseEntity<DealResponseDto> createDeal(@RequestBody DealRequestDto dto){
        return ResponseEntity.ok(dealService.createDeal(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DealResponseDto> updateDeal(@RequestBody DealRequestDto dto, @PathVariable Long id){
        return ResponseEntity.ok(dealService.updateDeal(dto,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeal(@PathVariable Long id){
        dealService.deleteDeal(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active")
    public Page<DealResponseDto> getActiveDeals(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam String sortDir
    ){
        return dealService.getActiveDeals(page,size,sortBy,sortDir);
    }
}
