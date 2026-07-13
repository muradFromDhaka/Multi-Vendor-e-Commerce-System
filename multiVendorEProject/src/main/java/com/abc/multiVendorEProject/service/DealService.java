package com.abc.multiVendorEProject.service;

import com.abc.multiVendorEProject.DTOs.projectDtos.DealRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.DealResponseDto;
import com.abc.multiVendorEProject.entity.Deal;
import com.abc.multiVendorEProject.entity.Product;
import com.abc.multiVendorEProject.mapper.DealMapper;
import com.abc.multiVendorEProject.repository.DealRepository;
import com.abc.multiVendorEProject.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DealService {

    private final DealMapper dealMapper;
    private final DealRepository dealRepository;
    private final ProductRepository productRepository;

    public Page<DealResponseDto> getAllDeals(int page, int size, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page,size, sort);

        return dealRepository.findAll(pageable)
                .map(d -> dealMapper.toDto(d));
    }

    public DealResponseDto getDealById(Long id){
        Deal deal = dealRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Deal not found with id"+ id));

        return dealMapper.toDto(deal);
    }

    @Transactional
    public DealResponseDto createDeal(DealRequestDto dto){
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(()-> new RuntimeException("Product not found with id"+ dto.productId()));

        Deal deal = dealMapper.toEntity(dto);

        deal.setProduct(product);
        Deal savedDeal = dealRepository.save(deal);

        return dealMapper.toDto(savedDeal);
    }

    @Transactional
    public DealResponseDto updateDeal(DealRequestDto dto, Long id) {
        Deal existingDeal = dealRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deal not found with id " + id));

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new RuntimeException("Product not found with id " + dto.productId()));

        dealMapper.updateEntity(existingDeal, dto);
        existingDeal.setProduct(product);

        Deal updateDeal = dealRepository.save(existingDeal);

        return dealMapper.toDto(updateDeal);
    }


    public void deleteDeal(Long id){
        Deal deal = dealRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deal not found with id " + id));

            dealRepository.delete(deal);
    }


    public Page<DealResponseDto> getActiveDeals(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page,size, sort);

        return dealRepository.findActiveDeals(pageable)
                .map(dealMapper::toDto);

    }

}


