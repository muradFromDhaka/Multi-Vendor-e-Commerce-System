package com.abc.multiVendorEProject.Controller;

import com.abc.multiVendorEProject.DTOs.projectDtos.CartDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.CartItemRequestDto;
import com.abc.multiVendorEProject.service.Customer.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // Get the cart of the current user
    @GetMapping
    public ResponseEntity<CartDto> getCart() {
        CartDto cartDto = cartService.getCart();
        return ResponseEntity.ok(cartDto);
    }

    // Add an item to the cart of the current user
    @PostMapping
    public ResponseEntity<CartDto> addItemToCart(@Valid @RequestBody CartItemRequestDto request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cartService.addItemToCart(request));

    }



    // Update a cart item for the current user
    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartDto> updateCartItem(
            @PathVariable Long cartItemId,
            @Valid @RequestBody CartItemRequestDto request) {

        return ResponseEntity.ok(cartService.updateCartItem(cartItemId, request));

    }

    // Remove a cart item for the current user
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long cartItemId) {

        cartService.removeCartItem(cartItemId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart() {

        cartService.clearCart();

        return ResponseEntity.noContent().build();
    }

}
