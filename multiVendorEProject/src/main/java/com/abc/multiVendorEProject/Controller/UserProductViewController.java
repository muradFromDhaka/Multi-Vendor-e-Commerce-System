package com.abc.multiVendorEProject.Controller;

import com.abc.multiVendorEProject.DTOs.projectDtos.UserProductViewResponseDto;
import com.abc.multiVendorEProject.service.UserProductViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/views")
@RequiredArgsConstructor
public class UserProductViewController {

    private final UserProductViewService viewService;


    @GetMapping("/me")
    public ResponseEntity<
            List<UserProductViewResponseDto>>
    getMyViews() {

        return ResponseEntity.ok(
                viewService.getMyViewedProducts());
    }

    @GetMapping("/product/{productId}/count")
    public ResponseEntity<Long> getViewCount(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                viewService.getProductViewCount(productId));
    }
}