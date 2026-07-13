import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/app/services/environments';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';
import { ProductDetailsResponse } from 'src/app/models/product.model';
import { AdminProductService } from '../../services/admin-product.service';
import { ProductVariantResponse } from '../../models/variants/productVariant.model';

@Component({
  selector: 'app-admin-product-details',
  templateUrl: './admin-product-details.component.html',
  styleUrls: ['./admin-product-details.component.scss']
})
export class AdminProductDetailsComponent implements OnInit {
  productId!: string | null;
  product!: ProductDetailsResponse;
  loading: boolean = true;
  errorMessage: string = '';
  selectedImageIndex: number = 0;
  baseImageUrl = environment.baseImageUrl;
  selectedVariant?: ProductVariantResponse;
  

  defaultImages: string[] = [
    'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500',
    'https://images.unsplash.com/photo-1526170375885-4d8ecf77b99f?w=500'
  ];

  constructor(
    private adminProductService: AdminProductService,
    public authService: AuthService,
    private cartService: CartService,
    private acRouter: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.productId = this.acRouter.snapshot.paramMap.get('id');

    if (this.productId) {
      this.adminProductService.getProductById(+this.productId)
        .subscribe({
          next: (res) => {
          this.product = res;
          if (res.variants?.length) {
              this.selectedVariant = res.variants[0];
            }
            this.loading = false;
        },
          error: () => {
            this.errorMessage = 'Failed to load product details.';
            this.loading = false;
          }
        });

    } else {
      this.errorMessage = 'No product ID provided in the URL.';
    }
  }

  get displayedImage(): string {

  if (this.product?.imageUrls?.length) {
    const img = this.product.imageUrls[this.selectedImageIndex];
    return img.startsWith('http')
      ? img
      : this.baseImageUrl + img;
  }
  return this.defaultImages[0];
}

  selectImage(index: number): void {
    this.selectedImageIndex = index;
  }

  formatDate(dateString?: string): string {
    return dateString ? new Date(dateString).toLocaleDateString() : 'N/A';
  }


  goBack(): void {
    this.router.navigate(['/admin/adminProductList']);
  }

  editProduct(): void {
    if (this.productId) {
      this.router.navigate(['/admin/adminEditProductForm', this.productId]);
    }
  }

   addToCart() {

  if (!this.selectedVariant) {
    return;
  }

  this.cartService.addItemToCart({
    variantId: this.selectedVariant.id,
    quantity: 1
  });
 }


 selectVariant(variant: ProductVariantResponse) {
  this.selectedVariant = variant;
}

get discountPercentage(): number {

  if (
  !this.selectedVariant ||
  !this.selectedVariant.price ||
  !this.selectedVariant.discountPrice
) {
  return 0;
}

  return Math.round(

    ((this.selectedVariant.price -
      this.selectedVariant.discountPrice)

      / this.selectedVariant.price) * 100

  );

}


}
