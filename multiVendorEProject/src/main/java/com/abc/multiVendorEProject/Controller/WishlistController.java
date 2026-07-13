package com.abc.multiVendorEProject.Controller;

import com.abc.multiVendorEProject.DTOs.projectDtos.WishlistDto.WishlistResponseDto;
import com.abc.multiVendorEProject.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping
    public ResponseEntity<WishlistResponseDto>
    getMyWishlist() {

        return ResponseEntity.ok(
                wishlistService.getMyWishlist());
    }

    @PostMapping("/{productId}")
    public ResponseEntity<WishlistResponseDto>
    addProduct(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                wishlistService.addProduct(productId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<WishlistResponseDto>
    removeProduct(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                wishlistService.removeProduct(productId));
    }

    @GetMapping("/exists/{productId}")
    public ResponseEntity<Boolean>
    existsInWishlist(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                wishlistService.existsInWishlist(productId));
    }
}
