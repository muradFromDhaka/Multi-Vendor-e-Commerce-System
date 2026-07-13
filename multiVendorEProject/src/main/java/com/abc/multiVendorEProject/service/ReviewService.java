package com.abc.multiVendorEProject.service;

import com.abc.multiVendorEProject.DTOs.projectDtos.ReviewRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.ReviewResponseDto;
import com.abc.multiVendorEProject.entity.Product;
import com.abc.multiVendorEProject.entity.Review;
import com.abc.multiVendorEProject.entity.User;
import com.abc.multiVendorEProject.mapper.ReviewMapper;
import com.abc.multiVendorEProject.repository.OrderItemRepository;
import com.abc.multiVendorEProject.repository.ProductRepository;
import com.abc.multiVendorEProject.repository.ReviewRepository;
import com.abc.multiVendorEProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
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
                        new RuntimeException("User not found"));

        Product product = productRepository.findById(
                        request.getProductId())
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        // Already reviewed?
        reviewRepository.findByUserAndProduct(user, product)
                .ifPresent(r -> {
                    throw new RuntimeException(
                            "You already reviewed this product");
                });

        // Purchased & Delivered?
        boolean purchased =
                orderItemRepository.hasPurchasedProduct(
                        user,
                        product
                );

        if (!purchased) {
            throw new RuntimeException(
                    "You can review only purchased products");
        }

        Review review = ReviewMapper.toEntity(request, user, product);

        reviewRepository.save(review);

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
                        new RuntimeException("Review not found"));

        if (!review.getUser().getUserName().equals(username)) {
            throw new RuntimeException(
                    "You can update only your own review");
        }

        review.setRating(request.getRating());
        review.setComment(request.getComment());

        reviewRepository.save(review);

        return ReviewMapper.toDto(review);
    }



    public List<ReviewResponseDto> getReviewsByProduct(
            Long productId) {

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"));

        return reviewRepository.findByProduct(product)
                .stream()
                .map(ReviewMapper::toDto)
                .toList();
    }

    public List<ReviewResponseDto> getMyReviews() {

        String username =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        User user = userRepository.findById(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return reviewRepository.findByUser(user)
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
                        new RuntimeException("Review not found"));

        if (!review.getUser().getUserName().equals(username)) {
            throw new RuntimeException(
                    "You can delete only your own review");
        }

        reviewRepository.delete(review);
    }


    private void updateProductReviewStats(Product product) {

        Double avgRating =
                reviewRepository.getAverageRatingByProduct(product);

        Long totalReviews =
                reviewRepository.countByProduct(product);

        product.setAverageRating(
                avgRating == null ? 0.0 : avgRating);

        product.setTotalReviews(
                totalReviews.intValue());

        productRepository.save(product);
    }

}