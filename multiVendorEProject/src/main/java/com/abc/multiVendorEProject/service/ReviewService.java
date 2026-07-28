package com.abc.multiVendorEProject.service;

import com.abc.multiVendorEProject.DTOs.projectDtos.ReviewDto.ProductReviewSummaryDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.ReviewDto.ReviewRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.ReviewDto.ReviewResponseDto;
import com.abc.multiVendorEProject.Util.BusinessException;
import com.abc.multiVendorEProject.Util.NotFoundException;
import com.abc.multiVendorEProject.entity.Product;
import com.abc.multiVendorEProject.entity.Review;
import com.abc.multiVendorEProject.entity.User;
import com.abc.multiVendorEProject.mapper.ReviewMapper;
import com.abc.multiVendorEProject.repository.OrderItemRepository;
import com.abc.multiVendorEProject.repository.ProductRepository;
import com.abc.multiVendorEProject.repository.ReviewRepository;
import com.abc.multiVendorEProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;

    public ReviewResponseDto createReview(ReviewRequestDto request) {

        String username =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        User user = userRepository.findById(username)
                .orElseThrow(() ->
                        new NotFoundException("User not found"));

        Product product = productRepository.findById(
                        request.getProductId())
                .orElseThrow(() ->
                        new NotFoundException("Product not found"));

        // Already reviewed?
        reviewRepository.findByUserAndProduct(user, product)
                .ifPresent(r -> {
                    throw new BusinessException(
                            "You already reviewed this product");
                });

        // Purchased & Delivered?
        boolean purchased =
                orderItemRepository.hasPurchasedProduct(
                        user,
                        product
                );

        if (!purchased) {
            throw new BusinessException(
                    "You can review only purchased products");
        }

        Review review = ReviewMapper.toEntity(request, user, product);

        reviewRepository.save(review);

        updateProductReviewStats(product);

        return ReviewMapper.toDto(review);
    }


    public ReviewResponseDto updateReview(
            Long reviewId,
            ReviewRequestDto request) {

        String username =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new NotFoundException("Review not found"));

        if (!review.getUser().getUserName().equals(username)) {
            throw new BusinessException(
                    "You can update only your own review");
        }

        review.setRating(request.getRating());
        review.setComment(request.getComment());

        reviewRepository.save(review);

        updateProductReviewStats(review.getProduct());

        return ReviewMapper.toDto(review);
    }



    public Page<ReviewResponseDto> getReviewsByProduct(
            Long productId, Pageable pageable) {

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "Product not found"));

        return reviewRepository.findByProduct(product,pageable)
                .map(ReviewMapper::toDto);

    }

    public List<ReviewResponseDto> getMyReviews() {

        String username =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        User user = userRepository.findById(username)
                .orElseThrow(() ->
                        new NotFoundException("User not found"));

        return reviewRepository.findByUser(user)
                .stream()
                .map(ReviewMapper::toDto)
                .toList();
    }



    public ProductReviewSummaryDto getProductReviewSummary(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new NotFoundException("Product not found"));

        ProductReviewSummaryDto dto =
                new ProductReviewSummaryDto();

        dto.setAverageRating(
                reviewRepository.getAverageRatingByProduct(product));

        dto.setTotalReviews(
                reviewRepository.countByProduct(product));

        dto.setFiveStar(
                reviewRepository.countByProductAndRating(product, 5.0));

        dto.setFourStar(
                reviewRepository.countByProductAndRating(product, 4.0));

        dto.setThreeStar(
                reviewRepository.countByProductAndRating(product, 3.0));

        dto.setTwoStar(
                reviewRepository.countByProductAndRating(product, 2.0));

        dto.setOneStar(
                reviewRepository.countByProductAndRating(product, 1.0));

        return dto;
    }


    public List<ReviewResponseDto> getLatestReviews() {
        return reviewRepository
                .findTop6ByOrderByCreatedAtDesc()
                .stream()
                .map(ReviewMapper::toDto)
                .toList();
    }


    public void deleteReview(Long reviewId) {

        String username =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new NotFoundException("Review not found"));

        if (!review.getUser().getUserName().equals(username)) {
            throw new BusinessException(
                    "You can delete only your own review");
        }

        Product product = review.getProduct();

        reviewRepository.delete(review);

        updateProductReviewStats(product);

    }


    private void updateProductReviewStats(Product product) {

        Double avgRating = reviewRepository.getAverageRatingByProduct(product);

        Long totalReviews = reviewRepository.countByProduct(product);

        product.setAverageRating(
                avgRating != null ? avgRating : 0.0);

        product.setTotalReviews(
                totalReviews != null ? totalReviews.intValue() : 0);

        productRepository.save(product);
    }

}