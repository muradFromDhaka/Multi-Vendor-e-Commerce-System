package com.abc.multiVendorEProject.service;

import com.abc.multiVendorEProject.DTOs.projectDtos.UserProductViewResponseDto;
import com.abc.multiVendorEProject.entity.Product;
import com.abc.multiVendorEProject.entity.User;
import com.abc.multiVendorEProject.entity.UserProductView;
import com.abc.multiVendorEProject.mapper.UserProductViewMapper;
import com.abc.multiVendorEProject.repository.ProductRepository;
import com.abc.multiVendorEProject.repository.UserProductViewRepository;
import com.abc.multiVendorEProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProductViewService {

    private final UserProductViewRepository viewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public void recordView(Long productId){

        Authentication auth =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        if(auth == null ||
                !auth.isAuthenticated() ||
                auth.getName().equals("anonymousUser")){
            return;
        }

        String username = auth.getName();

        User user = userRepository.findById(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new RuntimeException("Product not found"));

        UserProductView view =
                new UserProductView();

        view.setUser(user);
        view.setProduct(product);

        viewRepository.save(view);
    }

    public List<UserProductViewResponseDto>
    getMyViewedProducts() {

        String username =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        User user = userRepository.findById(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return viewRepository
                .findByUserOrderByViewedAtDesc(user)
                .stream()
                .map(UserProductViewMapper::toDto)
                .toList();
    }

    public long getProductViewCount(Long productId) {

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new RuntimeException("Product not found"));

        return viewRepository.countByProduct(product);
    }
}