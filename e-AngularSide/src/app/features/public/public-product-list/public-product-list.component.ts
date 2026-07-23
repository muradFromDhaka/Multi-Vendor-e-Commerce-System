import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductListResponse } from 'src/app/models/product.model';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';
import { environment } from 'src/app/services/environments';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-public-product-list',
  templateUrl: './public-product-list.component.html',
  styleUrls: ['./public-product-list.component.scss']
})
export class PublicProductListComponent implements OnInit{


products: ProductListResponse[] = [];
loading = false;
baseImageUrl = environment.baseImageUrl;

keyword = '';

page = 0;
size = 12;

totalElements = 0;
totalPages = 0;
  

    constructor(
      private productService: ProductService,
      private cartService: CartService,
      public authService: AuthService,
      private acRoute: ActivatedRoute,
      private router: Router
    ) {}
  

    ngOnInit(): void {


      this.acRoute.queryParams.subscribe(params => {

    this.keyword = params['keyword'] || '';

    this.page = 0;

    if (this.keyword) {
      this.searchProducts();
    } else {
      this.loadProducts();
    }

  });

}
  

loadProducts(): void {

  this.loading = true;

  this.productService
    .getAllProducts(this.page, this.size)
    .subscribe({

      next: (res) => {

        this.products = res.content;
        this.totalElements = res.totalElements;
        this.totalPages = res.totalPages;

        this.loading = false;
      },

      error: () => {
        this.loading = false;
      }

    });

}


searchProducts(): void {

  const keyword = this.keyword.trim();

  if (!keyword) {
    this.loadProducts();
    return;
  }

  this.loading = true;

  this.productService.searchProducts(
    keyword,
    this.page,
    this.size
  ).subscribe({

    next: (res) => {

      this.products = res.content;
      this.totalElements = res.totalElements;
      this.totalPages = res.totalPages;
      this.loading = false;

    },

    error: (err) => {

      console.error(err);
      this.loading = false;

    }

  });

}




previousPage(): void {

  if (this.page > 0) {

    this.page--;

    if (this.keyword) {
      this.searchProducts();
    } else {
      this.loadProducts();
    }

  }

}

nextPage(): void {

  if (this.page < this.totalPages - 1) {

    this.page++;

    if (this.keyword) {
      this.searchProducts();
    } else {
      this.loadProducts();
    }

  }

}

goToPage(page: number): void {

  this.page = page;

  if (this.keyword) {
    this.searchProducts();
  } else {
    this.loadProducts();
  }

}
  
  
addToCart(product: ProductListResponse): void {

        console.log(product);
        console.log(product.productVariantId);

      this.cartService.addItemToCart({
        productVariantId: product.productVariantId,
        quantity: 1});
    }


    getImageUrl(product: ProductListResponse): string {

  if (!product.thumbnailUrl) {
    return 'assets/images/no-image.png';
  }

  return product.thumbnailUrl.startsWith('http')
    ? product.thumbnailUrl
    : this.baseImageUrl + product.thumbnailUrl;
}
  
  
}
