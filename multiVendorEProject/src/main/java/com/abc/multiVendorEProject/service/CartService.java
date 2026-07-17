package com.abc.multiVendorEProject.service;

import com.abc.multiVendorEProject.DTOs.projectDtos.CartDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.CartItemRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.CartItemResponseDTO;
import com.abc.multiVendorEProject.Util.NotFoundException;
import com.abc.multiVendorEProject.entity.Cart;
import com.abc.multiVendorEProject.entity.CartItem;
import com.abc.multiVendorEProject.entity.Product;
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
                productVariantRepository.findById(request.getProductVariantId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductVariant().getId().equals(variant.getId()))
                .findFirst();

        CartItem cartItem;

        if (existingItem.isEmpty()) {
            cartItem = new CartItem();
            cartItem.setProductVariant(variant);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setCart(cart);

            // Set price & totalPrice
            cartItem.setUnitPrice(variant.getPrice());
            cartItem.calculateTotalPrice();

            cart.getItems().add(cartItem);
        } else {
            cartItem = existingItem.get();
            int newQuantity = cartItem.getQuantity() + request.getQuantity();
            cartItem.setQuantity(newQuantity);

            // Update price & totalPrice
            cartItem.setUnitPrice(variant.getPrice());
            cartItem.calculateTotalPrice();
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
        cartItem.setUnitPrice(cartItem.getProductVariant().getPrice());
        cartItem.calculateTotalPrice();

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

        cart.calculateTotalAmount();

        CartDto cartDto = new CartDto();
        cartDto.setCartId(cart.getId());
        cartDto.setTotalAmount(cart.getTotalAmount());

        cartDto.setTotalItems(
                cart.getItems()
                        .stream()
                        .mapToInt(CartItem::getQuantity)
                        .sum()
        );

        cartDto.setSubtotal(cart.getTotalAmount());

        cartDto.setShippingFee(BigDecimal.ZERO);

        cartDto.setDiscount(BigDecimal.ZERO);

        cartDto.setTotalAmount(
                cart.getTotalAmount()
                        .add(BigDecimal.ZERO)
                        .subtract(BigDecimal.ZERO)
        );

        List<CartItemResponseDTO> itemDtos = cart.getItems().stream().map(item -> {
            CartItemResponseDTO itemDto = new CartItemResponseDTO();
            itemDto.setCartItemId(item.getId());
            itemDto.setProductId(item.getProductVariant().getProduct().getId());
            itemDto.setProductVariantId(item.getProductVariant().getId());
            itemDto.setProductName(item.getProductVariant().getProduct().getName());
            itemDto.setSku(item.getProductVariant().getSku());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setUnitPrice(item.getUnitPrice());
            itemDto.setTotalPrice(item.getTotalPrice());

            // ---------------- Images ----------------
            ProductVariant variant = item.getProductVariant();

            Product product = variant.getProduct();

            if (product.getImageUrls() != null &&
                    !product.getImageUrls().isEmpty()) {

                itemDto.setImageUrl(
                        product.getImageUrls().get(0)
                );

            }
            else if (variant.getImageUrls() != null &&
                    !variant.getImageUrls().isEmpty()) {

                itemDto.setImageUrl(
                        variant.getImageUrls().get(0)
                );
            }

            // ---------------- Vendor Info ----------------
            if (item.getProductVariant().getProduct().getVendor() != null) {
                itemDto.setVendorId(item.getProductVariant().getProduct().getVendor().getId().intValue());
                itemDto.setVendorName(item.getProductVariant().getProduct().getVendor().getShopName());
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


    public void clearCart() {

        Cart cart = getCartEntity();

        cart.getItems().clear();

        cart.setTotalAmount(BigDecimal.ZERO);

        cartRepository.save(cart);
    }

}
