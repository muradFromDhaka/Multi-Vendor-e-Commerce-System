import { Component } from '@angular/core';
import { Router } from '@angular/router';
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
export class PublicProductListComponent {


    products: ProductListResponse[] = [];
    loading = false;
    baseImageUrl = environment.baseImageUrl;
  
    constructor(
      private productService: ProductService,
      private cartService: CartService,
      public authService: AuthService,
      private router: Router
    ) {}
  
    ngOnInit(): void {
      this.loadProducts();
    }
  
    loadProducts(): void {
      this.loading = true;
      this.productService.getAllProducts().subscribe({
        next: (res) => {
          this.products = res.content;
          this.loading = false;
        },
        error: () => {
          this.loading = false;
          // alert('Failed to load products');
        }
      });
    }
  
    editProduct(id: number): void {
      this.router.navigate(['/adminLayout/adminEditProductForm', id]);
    }
  
    deleteProduct(id: number): void {
      if (!confirm('Are you sure to delete this product?')) return;
      this.productService.deleteProduct(id).subscribe({
        next: () => {
          this.products = this.products.filter(p => p.id !== id);
        //  this.loadProducts()
        },
      });
    }
  
  
      addToCart(product: any): void {
      this.cartService.addItemToCart({
        variantId: product.variantId,
        quantity: 1
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
  
  
}
