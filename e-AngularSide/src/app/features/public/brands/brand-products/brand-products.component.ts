import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';
import { environment } from 'src/app/services/environments';
import { ProductListResponse } from 'src/app/models/product.model';
import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-brand-products',
  templateUrl: './brand-products.component.html',
  styleUrls: ['./brand-products.component.scss']
})
export class BrandProductsComponent implements OnInit {

  brandId?: number;
  brandName: string = '';
  products: ProductListResponse[] = [];
  loading = true;
  baseImageUrl = environment.baseImageUrl;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private productService: ProductService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.brandName = this.route.snapshot.paramMap.get('name') ?? '';
    this.route.paramMap.subscribe(params => {
      if (params.has('id')) {
        this.brandId = Number(params.get('id'));
        this.loadProductsByBrand();
      }
    });
  }

  loadProductsByBrand(): void {
    if (!this.brandId) return;

    this.loading = true;
    this.productService.getProductsByBrand(this.brandId).subscribe({
      next: res => {
        this.products = res.content;
        this.loading = false;
      },
      error: () => this.loading = false
    });
  }


  getImageUrl(product: ProductListResponse): string {

  if (!product.thumbnailUrl) {
    return 'assets/images/no-image.png';
  }

  return product.thumbnailUrl.startsWith('http')
    ? product.thumbnailUrl
    : this.baseImageUrl + product.thumbnailUrl;

}


addToCart(product: ProductListResponse): void {
  this.cartService.addItemToCart({
    productVariantId: product.productVariantId,
    quantity: 1
  });
}

goBack(): void {
  this.router.navigate(['/brands']);
}


}
