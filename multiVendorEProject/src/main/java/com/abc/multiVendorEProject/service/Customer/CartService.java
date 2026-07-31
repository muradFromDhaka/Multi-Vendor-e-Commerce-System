package com.abc.multiVendorEProject.service.Customer;

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
import java.util.Map;
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

        return mapCartToDto(cart);
    }


    private BigDecimal getSellingPrice(ProductVariant variant) {

        if (variant.getDiscountPrice() != null
                && variant.getDiscountPrice().compareTo(variant.getPrice()) < 0) {
            return variant.getDiscountPrice();
        }

        return variant.getPrice();
    }

    // ===========================================
// Add Item to Cart
// ===========================================
    public CartDto addItemToCart(CartItemRequestDto request) {

        User currentUser = getCurrentUser();

        Cart cart = cartRepository.findByUser(currentUser)
                .orElseGet(() -> createNewCartForUser(currentUser));

        ProductVariant variant = productVariantRepository
                .findById(request.getProductVariantId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductVariant().getId().equals(variant.getId()))
                .findFirst();

        CartItem cartItem;

        if (existingItem.isPresent()) {

            // Existing cart item
            cartItem = existingItem.get();
            cartItem.setQuantity(
                    cartItem.getQuantity() + request.getQuantity()
            );

        } else {

            // New cart item
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProductVariant(variant);
            cartItem.setQuantity(request.getQuantity());

            cart.getItems().add(cartItem);
        }

        // Update selling price & total price
        cartItem.setUnitPrice(getSellingPrice(variant));
        cartItem.calculateTotalPrice();

        // Update cart total
        cart.calculateTotalAmount();

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

        ProductVariant variant = cartItem.getProductVariant();

        cartItem.setQuantity(request.getQuantity());
        cartItem.setUnitPrice(getSellingPrice(variant));
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


    private BigDecimal calculateBulkDiscount(Cart cart) {

        int totalItems = cart.getItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();

        // Selling Price ভিত্তিক Subtotal
        BigDecimal subtotal = cart.getItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalItems >= 10) {
            return subtotal.multiply(PricingConstants.BULK_DISCOUNT_RATE_FOR_10_ITEMS);
        }

        if (totalItems >= 5) {
            return subtotal.multiply(PricingConstants.BULK_DISCOUNT_RATE_FOR_5_ITEMS);
        }

        return BigDecimal.ZERO;
    }


    private BigDecimal calculateSubtotal(Cart cart) {
        return cart.getItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }



    private BigDecimal calculateVendorDiscount(Cart cart) {

        BigDecimal totalDiscount = BigDecimal.ZERO;

        Map<Long, List<CartItem>> itemsByVendor = cart.getItems().stream()
                .collect(Collectors.groupingBy(
                        item -> item.getProductVariant().getProduct().getVendor().getId()
                ));

        for (Map.Entry<Long, List<CartItem>> entry : itemsByVendor.entrySet()) {

            BigDecimal vendorSubtotal = entry.getValue().stream()
                    .map(CartItem::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            int totalQuantity = entry.getValue().stream()
                    .mapToInt(CartItem::getQuantity)
                    .sum();

            if (totalQuantity >= 3) {

                totalDiscount = totalDiscount.add(
                        vendorSubtotal.multiply(PricingConstants.VENDOR_DISCOUNT_RATE)
                );

            }
        }

        return totalDiscount;
    }


    private BigDecimal calculateShippingFee(Cart cart) {

        if (cart.getItems().isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal subtotal = calculateSubtotal(cart);

        if (subtotal.compareTo(PricingConstants.FREE_SHIPPING_LIMIT) >= 0) {
            return BigDecimal.ZERO;
        }

        long vendorCount = cart.getItems().stream()
                .map(item -> item.getProductVariant().getProduct().getVendor().getId())
                .distinct()
                .count();

        long extraVendors = Math.max(0, vendorCount - 1);

        BigDecimal extraVendorFee =
                PricingConstants.EXTRA_VENDOR_FEE.multiply(BigDecimal.valueOf(extraVendors));

        return PricingConstants.BASE_SHIPPING_FEE.add(extraVendorFee);
    }




    private CartDto mapCartToDto(Cart cart) {

        CartDto cartDto = new CartDto();
        cartDto.setCartId(cart.getId());

        cartDto.setTotalItems(
                cart.getItems()
                        .stream()
                        .mapToInt(CartItem::getQuantity)
                        .sum()
        );


        // সুবটোটাল
        BigDecimal subtotal = calculateSubtotal(cart);

        cartDto.setSubtotal(subtotal);

        // ডিসকাউন্ট ক্যালকুলেশন
        BigDecimal totalDiscount = BigDecimal.ZERO;

        // 1. বাল্ক ডিসকাউন্ট
        totalDiscount = totalDiscount.add(calculateBulkDiscount(cart));

        // 2. ভেন্ডর ডিসকাউন্ট
        totalDiscount = totalDiscount.add(calculateVendorDiscount(cart));

        // ম্যাক্সিমাম ডিসকাউন্ট ৫০% এর বেশি না
        BigDecimal maxDiscount = subtotal.multiply(PricingConstants.MAX_DISCOUNT_RATE);
        if (totalDiscount.compareTo(maxDiscount) > 0) {
            totalDiscount = maxDiscount;
        }

        cartDto.setDiscount(totalDiscount);

        // শিপিং ফি
        BigDecimal shippingFee = calculateShippingFee(cart);
        cartDto.setShippingFee(shippingFee);

        // ফাইনাল টোটাল
        BigDecimal finalTotal = subtotal
                .subtract(totalDiscount)
                .add(shippingFee);

        if (finalTotal.compareTo(BigDecimal.ZERO) < 0) {
            finalTotal = BigDecimal.ZERO;
        }

        cartDto.setTotalAmount(finalTotal);

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

            // এখানে যোগ করবেন
            itemDto.setOriginalPrice(item.getProductVariant().getPrice());
            itemDto.setDiscountPrice(item.getProductVariant().getDiscountPrice());

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
