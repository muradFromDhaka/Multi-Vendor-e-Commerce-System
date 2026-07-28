package com.abc.multiVendorEProject.Controller.Customer;

import com.abc.multiVendorEProject.DTOs.projectDtos.vendorDto.WishlistDto.WishlistResponseDto;
import com.abc.multiVendorEProject.service.Customer.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('USER')")
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping
    public ResponseEntity<WishlistResponseDto>
    getMyWishlist() {

        return ResponseEntity.ok(
                wishlistService.getMyWishlist());
    }

    @PostMapping("/{productId}")
    public ResponseEntity<WishlistResponseDto> addProduct(
            @PathVariable Long productId) {

        System.out.println("productId----------------" + productId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        wishlistService.addProduct(productId)
                );
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
