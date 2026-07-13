package com.abc.multiVendorEProject.service.Vendor;

import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductDetailsResponseDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductListResponseDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.ProductDto.ProductRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.Variant.ProductVariantResponseDTO;
import com.abc.multiVendorEProject.entity.Brand;
import com.abc.multiVendorEProject.entity.Category;
import com.abc.multiVendorEProject.entity.Product;
import com.abc.multiVendorEProject.entity.Variant.ProductVariant;
import com.abc.multiVendorEProject.entity.Vendor;
import com.abc.multiVendorEProject.mapper.ProductMapper;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class VendorProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final VendorRepository vendorRepository;
    private final ProductVariantService productVariantService;
    private final FileStorageService fileStorageService;

    //===============================Helper Method==========================

    private Product getMyProductOrThrow(Long productId) {

        Vendor vendor = getCurrentVendor();

        System.out.println("vendor" + "-----------------------" + vendor);

        return productRepository
                .findByIdAndVendorIdAndDeletedFalse(
                        productId,
                        vendor.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Product not found."));
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

    private String getCurrentUsername() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Username : " + authentication.getName());
        System.out.println("Authorities : " + authentication.getAuthorities());

        return authentication.getName();
    }

    private Vendor getCurrentVendor() {

        String username = getCurrentUsername();

        return vendorRepository.findByUserUserName(username)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Vendor account not found."));
    }

    private Product save(Product product) {

        return productRepository.save(product);
    }

    private ProductListResponseDTO mapToProductListDto(Product product) {

        ProductVariant variant = product.getVariants()
                .stream()
                .findFirst()
                .orElse(null);

        BigDecimal price = BigDecimal.ZERO;
        BigDecimal discountPrice = null;

        if (variant != null) {
            price = variant.getPrice();
            discountPrice = variant.getDiscountPrice();
        }

        String thumbnail = null;
        if (product.getImageUrls() != null && !product.getImageUrls().isEmpty()) {
            thumbnail = product.getImageUrls().get(0);
        }

        return ProductMapper.toListDto(
                product,
                price,
                discountPrice,
                thumbnail,
                product.getVariants().size()
        );
    }


    private Page<ProductListResponseDTO> mapToProductList(Page<Product> products) {

        return products.map(this::mapToProductListDto);
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




//    ================================Original Methods===================================
//                  ======================================================

    @Transactional
    public ProductDetailsResponseDto createProduct(ProductRequestDto dto,
                                                   MultipartFile[] images){

        Product product = ProductMapper.toEntity(dto);

        product.setCategory(getCategoryOrThrow(dto.getCategoryId()));
        product.setBrand(getBrandOrThrow(dto.getBrandId()));

        product.setVendor(getCurrentVendor());

        product.setImageUrls(uploadImages(images));

        Product savedProduct = save(product);

        return getProductById(savedProduct.getId());
    }

    @Transactional
    public ProductDetailsResponseDto updateProduct(
            Long productId,
            ProductRequestDto dto,
            MultipartFile[] images) {

        Product product = getMyProductOrThrow(productId);

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

        product.setVendor(getCurrentVendor());

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


    public void deleteProduct(Long productId) {

        System.out.println("prodcutId:--------------- " + productId);

        Product product = getMyProductOrThrow(productId);

        if (product.isDeleted()) {
            throw new IllegalStateException("Product is already deleted.");
        }

        product.setDeleted(true);

        save(product);
    }


    @Transactional
    public ProductDetailsResponseDto getProductById(Long productId) {

        System.out.println("Step 1");
        Product product = getMyProductOrThrow(productId);

        System.out.println("Step 2");
        List<ProductVariantResponseDTO> variants =
                productVariantService.getVariantsByProduct(productId);

        System.out.println("Step 3");
        return ProductMapper.toDetailsDto(
                product,
                variants,
                variants.size()
        );
    }


    @Transactional
    public Page<ProductDetailsResponseDto> getMyProducts(Pageable pageable) {

        Vendor currentVendor = getCurrentVendor();

        return productRepository
                .findByVendorId(currentVendor.getId(), pageable)
                .map(product -> getProductById(product.getId()));
    }
}
