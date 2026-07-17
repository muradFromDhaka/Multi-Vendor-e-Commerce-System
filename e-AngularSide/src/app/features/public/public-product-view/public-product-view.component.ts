import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { environment } from 'src/app/services/environments';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';

import {
  ProductDetailsResponse,
  ProductListResponse
} from 'src/app/models/product.model';
import { ProductVariantResponse } from '../../../models/productVariant.model';

@Component({
  selector: 'app-public-product-view',
  templateUrl: './public-product-view.component.html',
  styleUrls: ['./public-product-view.component.scss']
})
export class PublicProductViewComponent implements OnInit {

  productId!: number;

  product!: ProductDetailsResponse;

  selectedVariant!: ProductVariantResponse;

  loading = true;

  errorMessage = '';

  selectedImageIndex = 0;

  baseImageUrl = environment.baseImageUrl;

  defaultImages = [
    'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500',
    'https://images.unsplash.com/photo-1526170375885-4d8ecf77b99f?w=500'
  ];

  constructor(
    private productService: ProductService,
    public authService: AuthService,
    private route: ActivatedRoute,
    private cartService: CartService,
    private location: Location
  ) {}

  ngOnInit(): void {

    const id = this.route.snapshot.paramMap.get('id');

    if (!id) {
      this.errorMessage = 'Invalid Product';
      this.loading = false;
      return;
    }

    this.productId = +id;

    this.loadProduct();
  }

  loadProduct() {

    this.loading = true;

    this.productService.getProductById(this.productId).subscribe({

      next: (res) => {

        this.product = res;

        if (res.productVariants.length > 0) {
          this.selectedVariant = res.productVariants[0];
        }

        this.loading = false;
      },

      error: () => {

        this.errorMessage = 'Failed to load product';

        this.loading = false;
      }

    });

  }

  //------------------------------------
  // Variant
  //------------------------------------

  selectVariant(variant: ProductVariantResponse) {

    this.selectedVariant = variant;

    this.selectedImageIndex = 0;

  }

  //------------------------------------
  // Images
  //------------------------------------

  get currentImages(): string[] {

    if (
      this.selectedVariant &&
      this.selectedVariant.imageUrls?.length > 0
    ) {
      return this.selectedVariant.imageUrls;
    }

    if (this.product.imageUrls?.length > 0) {
      return this.product.imageUrls;
    }

    return this.defaultImages;
  }

  get displayedImage(): string {

    const image = this.currentImages[this.selectedImageIndex];

    if (!image) {
      return this.defaultImages[0];
    }

    return image.startsWith('http')
      ? image
      : this.baseImageUrl + image;
  }

  selectImage(index: number) {

    this.selectedImageIndex = index;

  }

  //------------------------------------
  // Price
  //------------------------------------

  get price(): number {

    return this.selectedVariant?.price ?? 0;

  }

  get discountPrice(): number | undefined {

    return this.selectedVariant?.discountPrice;

  }

  get hasDiscount(): boolean {

    return !!this.discountPrice &&
      this.discountPrice < this.price;

  }

  calculateDiscountPercentage(): number {

    if (!this.hasDiscount) return 0;

    return Math.round(

      ((this.price - this.discountPrice!) / this.price) * 100

    );

  }

  //------------------------------------
  // Stock
  //------------------------------------

  get stock(): number {

    return this.selectedVariant?.stock ?? 0;

  }

  get isOutOfStock(): boolean {

    return this.stock <= 0;

  }

  //------------------------------------
  // Cart
  //------------------------------------

  addToCart() {

    if (!this.selectedVariant) return;

    this.cartService.addItemToCart({

      productVariantId: this.selectedVariant.id,

      quantity: 1

    });

  }

  //------------------------------------
  // Utilities
  //------------------------------------

  formatDate(date?: string): string {

    return date
      ? new Date(date).toLocaleDateString()
      : 'N/A';

  }


  getVariantName(variant: ProductVariantResponse): string {
  return variant.attributeValues
    .map(value => value.value)
    .join(' / ');
}

  goBack() {

    this.location.back();

  }

}