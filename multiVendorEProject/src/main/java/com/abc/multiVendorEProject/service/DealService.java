package com.abc.multiVendorEProject.service;

import com.abc.multiVendorEProject.DTOs.projectDtos.DealRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.DealResponseDto;
import com.abc.multiVendorEProject.entity.Deal;
import com.abc.multiVendorEProject.entity.Product;
import com.abc.multiVendorEProject.mapper.DealMapper;
import com.abc.multiVendorEProject.repository.DealRepository;
import com.abc.multiVendorEProject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DealService {

    private final DealRepository dealRepository;
    private final ProductRepository productRepository;
    private final DealMapper dealMapper;

    // ==========================================
    // Get All Deals (Admin)
    // ==========================================
    @Transactional(readOnly = true)
    public Page<DealResponseDto> getAllDeals(
            int page,
            int size,
            String sortBy,
            String sortDir) {

        return dealRepository
                .findAll(createPageable(page, size, sortBy, sortDir))
                .map(dealMapper::toDto);
    }

    // ==========================================
    // Get Deal By Id
    // ==========================================
    @Transactional(readOnly = true)
    public DealResponseDto getDealById(Long id) {

        Deal deal = dealRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Deal not found with id: " + id));

        return dealMapper.toDto(deal);
    }

    // ==========================================
    // Create Deal
    // ==========================================
    public DealResponseDto createDeal(DealRequestDto dto) {

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() ->
                        new RuntimeException("Product not found with id: " + dto.productId()));

        Deal deal = dealMapper.toEntity(dto);

        deal.setProduct(product);

        Deal savedDeal = dealRepository.save(deal);

        return dealMapper.toDto(savedDeal);
    }

    // ==========================================
    // Update Deal
    // ==========================================
    public DealResponseDto updateDeal(Long id, DealRequestDto dto) {

        Deal deal = dealRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Deal not found with id: " + id));

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() ->
                        new RuntimeException("Product not found with id: " + dto.productId()));

        dealMapper.updateEntity(deal, dto);

        deal.setProduct(product);

        Deal updatedDeal = dealRepository.save(deal);

        return dealMapper.toDto(updatedDeal);
    }

    // ==========================================
    // Delete Deal
    // ==========================================
    public void deleteDeal(Long id) {

        Deal deal = dealRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Deal not found with id: " + id));

        dealRepository.delete(deal);
    }

    // ==========================================
    // Active Deals (Home Page)
    // ==========================================
    @Transactional(readOnly = true)
    public Page<DealResponseDto> getActiveDeals(
            int page,
            int size,
            String sortBy,
            String sortDir) {

        return dealRepository
                .findActiveDeals(createPageable(page, size, sortBy, sortDir))
                .map(dealMapper::toDto);
    }

    // ==========================================
    // Pageable Helper
    // ==========================================
    private Pageable createPageable(
            int page,
            int size,
            String sortBy,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        return PageRequest.of(page, size, sort);
    }


    @Transactional(readOnly = true)
    public DealResponseDto getActiveDealByProduct(Long productId) {

        Deal deal = dealRepository.findActiveDealByProductId(productId)
                .orElseThrow(() ->
                        new RuntimeException("No active deal found for this product"));

        return dealMapper.toDto(deal);
    }

}