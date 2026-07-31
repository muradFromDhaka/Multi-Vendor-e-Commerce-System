package com.abc.multiVendorEProject.Controller;

import com.abc.multiVendorEProject.DTOs.projectDtos.ReviewDto.ProductReviewSummaryDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.ReviewDto.ReviewRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.ReviewDto.ReviewResponseDto;
import com.abc.multiVendorEProject.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // ==========================
    // Create Review
    // ==========================
    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(
            @Valid @RequestBody ReviewRequestDto request) {

        return ResponseEntity.ok(
                reviewService.createReview(request));
    }

    // ==========================
    // Update Review
    // ==========================
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewRequestDto request) {

        return ResponseEntity.ok(
                reviewService.updateReview(reviewId, request));
    }

    // ==========================
    // Delete Review
    // ==========================
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long reviewId) {

        reviewService.deleteReview(reviewId);

        return ResponseEntity.ok("Review deleted successfully");
    }

    // ==========================
    // Product Reviews (Paginated)
    // ==========================
    @GetMapping("/product/{productId}")
    public ResponseEntity<Page<ReviewResponseDto>> getProductReviews(
            @PathVariable Long productId,
            Pageable pageable) {

        return ResponseEntity.ok(
                reviewService.getReviewsByProduct(productId, pageable));
    }

    // ==========================
    // Product Review Summary
    // ==========================
    @GetMapping("/product/{productId}/summary")
    public ResponseEntity<ProductReviewSummaryDto> getProductReviewSummary(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                reviewService.getProductReviewSummary(productId));
    }

    // ==========================
    // Logged-in User Reviews
    // ==========================
    @GetMapping("/me")
    public ResponseEntity<List<ReviewResponseDto>> getMyReviews() {

        return ResponseEntity.ok(
                reviewService.getMyReviews());
    }


    @GetMapping("/latest")
    public ResponseEntity<List<ReviewResponseDto>> getLatestReviews() {
        return ResponseEntity.ok(reviewService.getLatestReviews());
    }

}