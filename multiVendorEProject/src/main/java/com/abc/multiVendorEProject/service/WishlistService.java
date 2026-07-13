package com.abc.multiVendorEProject.service;

import com.abc.multiVendorEProject.DTOs.projectDtos.WishlistDto.WishlistResponseDto;
import com.abc.multiVendorEProject.entity.Product;
import com.abc.multiVendorEProject.entity.User;
import com.abc.multiVendorEProject.entity.Wishlist;
import com.abc.multiVendorEProject.mapper.WishlistMapper;
import com.abc.multiVendorEProject.repository.ProductRepository;
import com.abc.multiVendorEProject.repository.UserRepository;
import com.abc.multiVendorEProject.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final WishlistMapper wishlistMapper;

    public WishlistResponseDto getMyWishlist() {

        User user = getCurrentUser();

        Wishlist wishlist =
                wishlistRepository.findByUser(user)
                        .orElseGet(() -> createWishlist(user));

        return wishlistMapper.toDto(wishlist);
    }

    public WishlistResponseDto addProduct(
            Long productId) {

        User user = getCurrentUser();

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"));

        Wishlist wishlist = wishlistRepository.findByUser(user)
                        .orElseGet(() -> createWishlist(user));

        if (wishlistRepository.existsByUserAndProductsContaining(user, product)) {

            throw new RuntimeException("Product already exists in wishlist");
        }

        wishlist.getProducts().add(product);

        return wishlistMapper.toDto(
                wishlistRepository.save(wishlist));
    }

    public WishlistResponseDto removeProduct(
            Long productId) {

        User user = getCurrentUser();

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException("Product not found"));

        Wishlist wishlist = wishlistRepository.findByUser(user)
                        .orElseThrow(() -> new RuntimeException("Wishlist not found"));

        if (!wishlistRepository.existsByUserAndProductsContaining(user, product)) {

            throw new RuntimeException("Product does not exist in wishlist");

        }

        wishlist.getProducts().remove(product);

        return wishlistMapper.toDto(
                wishlistRepository.save(wishlist));
    }

    public boolean existsInWishlist(
            Long productId) {

        User user = getCurrentUser();

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"));

        return wishlistRepository
                .existsByUserAndProductsContaining(
                        user,
                        product);
    }

    private Wishlist createWishlist(
            User user) {

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);

        return wishlistRepository.save(wishlist);
    }

    private User getCurrentUser() {

        String username =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

    }
}
