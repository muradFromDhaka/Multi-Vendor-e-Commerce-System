package com.abc.multiVendorEProject.Controller;

import com.abc.multiVendorEProject.DTOs.projectDtos.ReviewRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.ReviewResponseDto;
import com.abc.multiVendorEProject.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // Create Review
    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(
            @RequestBody ReviewRequestDto request) {

        return ResponseEntity.ok(
                reviewService.createReview(request));
    }

    // Update Review
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewRequestDto request) {

        return ResponseEntity.ok(
                reviewService.updateReview(reviewId, request));
    }

    // Delete Review
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long reviewId) {

        reviewService.deleteReview(reviewId);

        return ResponseEntity.ok(
                "Review deleted successfully");
    }

    // Get Reviews By Product
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponseDto>>
    getProductReviews(@PathVariable Long productId) {

        return ResponseEntity.ok(
                reviewService.getReviewsByProduct(productId));
    }

    // Get Logged-in User Reviews
    @GetMapping("/me")
    public ResponseEntity<List<ReviewResponseDto>>
    getMyReviews() {

        return ResponseEntity.ok(
                reviewService.getMyReviews());
    }
}