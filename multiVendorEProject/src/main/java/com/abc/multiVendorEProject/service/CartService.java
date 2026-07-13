package com.abc.multiVendorEProject.service;

import com.abc.multiVendorEProject.DTOs.projectDtos.CartDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.CartItemRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.CartItemResponseDTO;
import com.abc.multiVendorEProject.Util.NotFoundException;
import com.abc.multiVendorEProject.entity.Cart;
import com.abc.multiVendorEProject.entity.CartItem;
import com.abc.multiVendorEProject.entity.User;
import com.abc.multiVendorEProject.entity.Variant.ProductVariant;
import com.abc.multiVendorEProject.repository.CartRepository;
import com.abc.multiVendorEProject.repository.UserRepository;
import com.abc.multiVendorEProject.repository.VariantRepository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final ProductVariantRepository productVariantRepository;
    private final UserRepository userRepository;

    // ===========================================
    // Get Current Logged-in User
    // ===========================================
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()
                || auth instanceof AnonymousAuthenticationToken) {
            throw new NotFoundException.UnauthorizedException("User not logged in");
        }

        String username = auth.getName();
        return userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    // ===========================================
    // Get Cart for Logged-in User
    // ===========================================
    public CartDto getCart() {
        User currentUser = getCurrentUser();
        Cart cart = cartRepository.findByUser(currentUser)
                .orElseGet(() -> createNewCartForUser(currentUser));

        cart.calculateTotalAmount(); // Calculate total
        return mapCartToDto(cart);
    }

    // ===========================================
    // Add Item to Cart
    // ===========================================
    public CartDto addItemToCart(CartItemRequestDto request) {
        User currentUser = getCurrentUser();


        Cart cart = cartRepository.findByUser(currentUser)
                .orElseGet(() -> createNewCartForUser(currentUser));

        ProductVariant variant =
                productVariantRepository.findById(request.getVariantId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getVariant().getId().equals(variant.getId()))
                .findFirst();

        CartItem cartItem;

        if (existingItem.isEmpty()) {
            cartItem = new CartItem();
            cartItem.setVariant(variant);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setCart(cart);

            // Set price & totalPrice
            cartItem.setUnitPrice(variant.getPrice());
            cartItem.setTotalPrice(variant.getPrice().multiply(new BigDecimal(request.getQuantity())));

            cart.getItems().add(cartItem);
        } else {
            cartItem = existingItem.get();
            int newQuantity = cartItem.getQuantity() + request.getQuantity();
            cartItem.setQuantity(newQuantity);

            // Update price & totalPrice
            cartItem.setUnitPrice(variant.getPrice());
            cartItem.setTotalPrice(variant.getPrice().multiply(new BigDecimal(newQuantity)));
        }

        cart.calculateTotalAmount(); // Update cart total
        cartRepository.save(cart);
        return mapCartToDto(cart);
    }

    // ===========================================
    // Update Cart Item Quantity
    // ===========================================
    public CartDto updateCartItem(Long cartItemId, CartItemRequestDto request) {
        Cart cart = getCartEntity();

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItem.setQuantity(request.getQuantity());
        cartItem.setUnitPrice(cartItem.getVariant().getPrice());
        cartItem.setTotalPrice(cartItem.getVariant().getPrice()
                .multiply(new BigDecimal(request.getQuantity())));

        cart.calculateTotalAmount();
        cartRepository.save(cart);
        return mapCartToDto(cart);
    }

    // ===========================================
    // Remove Cart Item
    // ===========================================
    public void removeCartItem(Long cartItemId) {
        Cart cart = getCartEntity();

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItem.setCart(null);
        cart.getItems().remove(cartItem);

        cart.calculateTotalAmount();
        cartRepository.save(cart);
    }

    // ===========================================
    // Helper: Create new cart for user
    // ===========================================
    private Cart createNewCartForUser(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalAmount(BigDecimal.ZERO);
        return cartRepository.save(cart);
    }


    private CartDto mapCartToDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setCartId(cart.getId());
        cartDto.setTotalAmount(cart.getTotalAmount());

        List<CartItemResponseDTO> itemDtos = cart.getItems().stream().map(item -> {
            CartItemResponseDTO itemDto = new CartItemResponseDTO();
            itemDto.setItemId(item.getId());
            itemDto.setProductId(item.getVariant().getId());
            itemDto.setProductName(item.getVariant().getProduct().getName());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setUnitPrice(item.getUnitPrice());
            itemDto.setTotalPrice(item.calculateTotalPrice());

            // ---------------- Images ----------------
            try {
                List<String> images = item.getVariant().getProduct().getImageUrls();
                if (images != null && !images.isEmpty()) {
                    itemDto.setImageUrl(images.get(0));
                }
            } catch (Exception e) {
                System.out.println("Error accessing image: " + e.getMessage());
            }

            // ---------------- Vendor Info ----------------
            if (item.getVariant().getProduct().getVendor() != null) {
                itemDto.setVendorId(item.getVariant().getProduct().getVendor().getId().intValue());
                itemDto.setVendorName(item.getVariant().getProduct().getVendor().getShopName());
            } else {
                itemDto.setVendorId(0); // fallback
                itemDto.setVendorName("Unknown");
            }

            return itemDto;
        }).collect(Collectors.toList());

        cartDto.setItems(itemDtos);
        return cartDto;
    }

    // ===========================================
    // Helper: Get Cart Entity
    // ===========================================
    public Cart getCartEntity() {
        User currentUser = getCurrentUser();
        return cartRepository.findByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }
}
