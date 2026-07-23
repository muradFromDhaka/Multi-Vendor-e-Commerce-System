package com.abc.multiVendorEProject.service.Admin;

import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductDetailsResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductListResponseDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductRequestDto;
import com.abc.multiVendorEProject.entity.Brand;
import com.abc.multiVendorEProject.entity.Category;
import com.abc.multiVendorEProject.entity.Product;
import com.abc.multiVendorEProject.entity.Variant.ProductVariant;
import com.abc.multiVendorEProject.entity.Vendor;
import com.abc.multiVendorEProject.enums.ProductStatus;
import com.abc.multiVendorEProject.mapper.ProductMapper;
import com.abc.multiVendorEProject.mapper.Variant.ProductVariantMapper;
import com.abc.multiVendorEProject.repository.BrandRepository;
import com.abc.multiVendorEProject.repository.CategoryRepository;
import com.abc.multiVendorEProject.repository.ProductRepository;
import com.abc.multiVendorEProject.repository.VendorRepository;
import com.abc.multiVendorEProject.service.FileStorageService;
import com.abc.multiVendorEProject.service.VariantService.ProductVariantService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final VendorRepository vendorRepository;
    private final FileStorageService fileStorageService;

    // ================= Helper =================

    private Product getProductOrThrow(Long productId) {

        return productRepository.findByIdAndDeletedFalse(productId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Product not found with id : " + productId));
    }

    private Category getCategoryOrThrow(Long categoryId) {

        return categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Category not found with id : " + categoryId));
    }

    private Brand getBrandOrThrow(Long brandId) {

        return brandRepository.findById(brandId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Brand not found with id : " + brandId));
    }

    private Vendor getVendorOrThrow(Long vendorId) {

        return vendorRepository.findById(vendorId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Vendor not found with id : " + vendorId));
    }

    private Product save(Product product) {
        return productRepository.save(product);
    }


    private Page<ProductListResponseDTO> mapToProductList(Page<Product> products) {
        return products.map(ProductMapper::toListDto);
    }


    private List<String> uploadImages(MultipartFile[] images) {

        if (images == null || images.length == 0) {
            return new ArrayList<>();
        }

        return Arrays.stream(images)
                .filter(file -> file != null && !file.isEmpty())
                .map(fileStorageService::saveFile)
                .collect(Collectors.toCollection(ArrayList::new));
    }


    // ================= Product Management =================


    public ProductDetailsResponseDto createProduct(ProductRequestDto dto,
                                                   MultipartFile[] images){

        Product product = ProductMapper.toEntity(dto);

        product.setCategory(getCategoryOrThrow(dto.getCategoryId()));
        product.setBrand(getBrandOrThrow(dto.getBrandId()));

        if (dto.getStatus() == null) {
            product.setStatus(ProductStatus.DRAFT);
        }


        if (dto.getVendorId() == null) {
            throw new IllegalArgumentException("Vendor is required.");
        }

        product.setVendor(getVendorOrThrow(dto.getVendorId()));

        product.setImageUrls(uploadImages(images));

        Product savedProduct = save(product);

        return getProductById(savedProduct.getId());
    }

    public ProductDetailsResponseDto updateProduct(
            Long productId,
            ProductRequestDto dto,
            MultipartFile[] images) {

        Product product = getProductOrThrow(productId);

        // Basic Information
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setReleaseDate(dto.getReleaseDate());

        if (dto.getStatus() != null) {
            product.setStatus(dto.getStatus());
        }

        // Relations
        product.setCategory(getCategoryOrThrow(dto.getCategoryId()));
        product.setBrand(getBrandOrThrow(dto.getBrandId()));

        if (dto.getVendorId() != null) {
            product.setVendor(getVendorOrThrow(dto.getVendorId()));
        }

        // ================= IMAGE UPDATE START =================

        List<String> oldImages = new ArrayList<>(product.getImageUrls());

        List<String> finalImages = new ArrayList<>();

        if (dto.getImageUrls() != null) {
            finalImages.addAll(dto.getImageUrls());
        }

        if (images != null && images.length > 0) {
            finalImages.addAll(uploadImages(images));
        }

        oldImages.stream()
                .filter(img -> !finalImages.contains(img))
                .forEach(fileStorageService::deleteFile);

        product.setImageUrls(finalImages);

        // ================= IMAGE UPDATE END =================

        Product updatedProduct = save(product);

        return getProductById(updatedProduct.getId());

    }



    @Transactional
    public Page<ProductListResponseDTO> getAllProducts(Pageable pageable) {

        return mapToProductList(
                productRepository.findByDeletedFalse(pageable)
        );
    }

    @Transactional
    public ProductDetailsResponseDto getProductById(Long productId) {

        Product product = getProductOrThrow(productId);

        return ProductMapper.toDetailsDto(
                product,
                product.getVariants()
                        .stream()
                        .map(ProductVariantMapper::toResponse)
                        .toList(),
                product.getVariants().size()
        );
    }

    public void deleteProduct(Long productId) {

        Product product = getProductOrThrow(productId);

        product.setDeleted(true);

        save(product);

    }

    public ProductDetailsResponseDto changeProductStatus(
            Long productId,
            ProductStatus status) {

        Product product = getProductOrThrow(productId);

        product.setStatus(status);

        save(product);

        return getProductById(productId);
    }


    @Transactional
    public Page<ProductListResponseDTO> searchProducts(
            String keyword,
            Pageable pageable) {

        return mapToProductList(
                productRepository.searchProducts(
                        keyword,
                        pageable
                )
        );
    }

}