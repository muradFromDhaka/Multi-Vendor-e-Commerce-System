
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/app/services/environments';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';
import { ProductListResponse } from 'src/app/models/product.model';

@Component({
  selector: 'app-category-products',
  templateUrl: './category-products.component.html',
  styleUrls: ['./category-products.component.scss'],
})
export class CategoryProductsComponent implements OnInit {

  // 🔹 Category
  categoryId?: number;
  categoryName?: string;

  // 🔹 Search
  searchQuery?: string;

  // 🔹 Common
  products: ProductListResponse[] = [];
  loading = false;
  baseImageUrl = environment.baseImageUrl;


  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private cartService: CartService,
    private productService: ProductService
  ) {}

  ngOnInit(): void {

    // 🔹 Route Params (categoryId)
    this.route.paramMap.subscribe(params => {
      if (params.has('id')) {
        this.categoryId = Number(params.get('id'));
        this.loadProductsByCategoryId();
      }

      if (params.has('name')) {
        this.categoryName = params.get('name') || '';
      }
    });

    // 🔹 Query Params (search)
    this.route.queryParamMap.subscribe(params => {
      if (params.has('query')) {
        this.searchQuery = params.get('query') || '';
        this.searchProducts();
      }
    });
  }

  // ===============================
  // Category based products
  // ===============================
  loadProductsByCategoryId(): void {
    if (!this.categoryId) return;

    this.loading = true;
    this.products = [];

    this.productService.getProductsByCategoryId(this.categoryId).subscribe({
      next: res => {
        this.products = res.content;
        this.loading = false;
      },
      error: () => this.loading = false
    });
  }

  // ===============================
  // Universal search
  // ===============================
  searchProducts(): void {
    if (!this.searchQuery) return;

    this.loading = true;
    this.products = [];

    this.productService.searchProducts(this.searchQuery).subscribe({
      next: res => {
        this.products = res.content;
        this.loading = false;
      },
      error: () => this.loading = false
    });
  }


  addToCart(product: ProductListResponse): void {

  if (!product.productVariantId) {
    alert('No variant available for this product.');
    return;
  }

  this.cartService.addItemToCart({
    productVariantId: product.productVariantId,
    quantity: 1
  });

}


goBack(): void {
  this.router.navigate(['/categories']);
}

}

