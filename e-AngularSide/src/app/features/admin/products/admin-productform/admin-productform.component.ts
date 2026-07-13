import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import {
  ProductDetailsResponse,
  ProductRequest,
  ProductStatus,
} from 'src/app/models/product.model';
import { AuthService } from 'src/app/services/auth.service';
import { BrandService } from 'src/app/services/brand.service';
import { CategoryService } from 'src/app/services/category.service';
import { BrandResponse } from 'src/app/models/brand.model';
import { CategoryResponse } from 'src/app/models/category.model';
import { VendorResponse } from 'src/app/models/vendor.model';
import { finalize } from 'rxjs';
import { environment } from 'src/app/services/environments';
import { AdminVendorService } from '../../services/admin-vendor.service';
import { AdminProductService } from '../../services/admin-product.service';

@Component({
  selector: 'app-admin-productform',
  templateUrl: './admin-productform.component.html',
  styleUrls: ['./admin-productform.component.scss'],
})
export class AdminProductformComponent implements OnInit {
  productForm!: FormGroup;
  isEditMode = false;
  productId?: number;

  brands: BrandResponse[] = [];
  categories: CategoryResponse[] = [];
  vendors: VendorResponse[] = [];

  existingImages: string[] = [];
  newImages: File[] = [];
  previewImages: string[] = [];

  productStatuses = Object.values(ProductStatus);

  loading = false;
  isAdmin = false;
  baseImageUrl = environment.baseImageUrl;

  constructor(
    private fb: FormBuilder,
    private adminProductService: AdminProductService,
    private brandService: BrandService,
    private categoryService: CategoryService,
    private adminVendorService: AdminVendorService,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const currentUser = this.authService.getCurrentUser();
    this.isAdmin = this.authService.hasRole('ROLE_ADMIN');

    this.productId = this.route.snapshot.params['id'];
    this.isEditMode = !!this.productId;

    // Initialize form
    this.productForm = this.fb.group({
      name: ['', [Validators.required, Validators.maxLength(100)]],
      description: ['', [Validators.maxLength(1000)]],
      status: [ProductStatus.DRAFT],
      releaseDate: [''],
      categoryId: [null, Validators.required],
      brandId: [null, Validators.required],
      vendorId: [{ value: null, disabled: !this.isAdmin }],
    });

    this.loadBrands();
    this.loadCategories();
    if (this.isAdmin) this.loadVendors();

    if (!this.isAdmin && currentUser?.vendorId) {
      this.productForm.patchValue({ vendorId: currentUser.vendorId });
    }

    if (this.isEditMode && this.productId) {
      this.loadProduct(this.productId);
    }

  }

  private loadBrands() {
    this.brandService.getAll().subscribe((res) => (this.brands = res));
  }

  private loadCategories() {
    this.categoryService
      .getAllCategories()
      .subscribe((res) => (this.categories = res.content));
  }

  private loadVendors() {
    this.adminVendorService.getVendors().subscribe((res) => (this.vendors = res));
  }

  private loadProduct(id: number) {
    this.loading = true;
    this.adminProductService
      .getProductById(id)
      .pipe(finalize(() => (this.loading = false)))
      .subscribe({
        next: (res: ProductDetailsResponse) => {
          this.productForm.patchValue({
            ...res,
            status: res.status ?? ProductStatus.DRAFT,
            releaseDate: res.releaseDate ?? '',
          });
          if (res.imageUrls) {
            this.existingImages = [...res.imageUrls];
            this.previewImages = this.existingImages.map(img => this.baseImageUrl + img);
          }
        },
        error: (err) => console.error(err),
      });
  }

  onFileChange(event: any) {
    if (event.target.files && event.target.files.length) {
      const files = Array.from(event.target.files) as File[];
      this.newImages.push(...files);

      files.forEach((file) => {
        const reader = new FileReader();
        reader.onload = (e: any) => this.previewImages.push(e.target.result);
        reader.readAsDataURL(file);
      });
    }
  }

 removeImage(index: number) {

  const previewImg = this.previewImages[index];

  this.previewImages.splice(index, 1);

  const existingIndex = this.existingImages.findIndex(
    img => this.baseImageUrl + img === previewImg
  );

  if (existingIndex >= 0) {

    this.existingImages.splice(existingIndex, 1);

  } else {

    const newImageIndex = index - this.existingImages.length;

    if (newImageIndex >= 0) {
      this.newImages.splice(newImageIndex, 1);
    }

  }
}


  submit() {


    console.log(this.productForm.errors);

Object.keys(this.productForm.controls).forEach(key => {
  const control = this.productForm.get(key);

  console.log(
    key,
    control?.value,
    control?.valid,
    control?.errors
  );
});


  if (this.productForm.invalid) {
    this.productForm.markAllAsTouched();
    return;
  }

  const dto: ProductRequest = {
    ...this.productForm.getRawValue(),
    status: this.productForm.value.status ?? ProductStatus.DRAFT,
    releaseDate: this.productForm.value.releaseDate || null,
    imageUrls: this.existingImages
  };

  // ===== ADD THESE =====
  console.log("DTO =", dto);
  console.log("Before updateProduct");
  // =====================

  this.loading = true;

  const request$ =
    this.isEditMode && this.productId
      ? this.adminProductService.updateProduct(this.productId, dto, this.newImages)
      : this.adminProductService.createProduct(dto, this.newImages);

  // ===== ADD THIS =====
  console.log("After updateProduct");
  // ====================

  request$.pipe(finalize(() => (this.loading = false))).subscribe({
    next: (res) => {

      console.log("NEXT CALLED");
      console.log(res);

      this.router.navigate(['/admin/adminProductList']);

      console.log("NAVIGATION CALLED");
    },
    error: (err) => {
      console.log("ERROR");
      console.log(err);
      alert('Error occurred while saving product.');
    },
  });
}

  // Helper for template
  get f() {
    return this.productForm.controls;
  }

}
