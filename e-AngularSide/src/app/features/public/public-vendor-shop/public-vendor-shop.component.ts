import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ProductDetailsResponse, ProductListResponse } from 'src/app/models/product.model';
import { VendorResponse } from 'src/app/models/vendor.model';

import { ProductService } from 'src/app/services/product.service';
import { CartService } from 'src/app/services/cart.service';
import { AuthService } from 'src/app/services/auth.service';

import { WishlistService } from '../../customer/services/wishlist.service';
import { VendorService } from '../../vendor/services/vendor.service';

@Component({
  selector: 'app-public-vendor-shop',
  templateUrl: './public-vendor-shop.component.html',
  styleUrls: ['./public-vendor-shop.component.scss']
})
export class PublicVendorShopComponent implements OnInit {

  vendor!: VendorResponse;

  products: ProductListResponse[] = [];

  loading = true;

  vendorId!: number;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private vendorService: VendorService,
    private productService: ProductService,
    private wishlistService: WishlistService,
    private cartService: CartService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {

    this.vendorId = Number(
      this.route.snapshot.paramMap.get('id')
    );

    this.loadVendor();

    this.loadProducts();

  }

  loadVendor(): void {

    this.vendorService
      .getVendorById(this.vendorId)
      .subscribe({

        next: (vendor) => {

          this.vendor = vendor;

        },

        error: console.error

      });

  }

  loadProducts(): void {

    this.loading = true;

    this.productService
      .getProductsByVendor(this.vendorId)
      .subscribe({

        next: (res) => {

          this.products = res.content;

          this.loading = false;

        },

        error: (err) => {

          console.error(err);

          this.loading = false;

        }

      });

  }

  handleWishlist(product: ProductListResponse): void {

    if (!this.authService.isLoggedIn()) {

      this.router.navigate(['/auth/login']);

      return;

    }

    if (product.inWishlist) {

      this.wishlistService
        .removeFromWishlist(product.id)
        .subscribe(() => product.inWishlist = false);

    } else {

      this.wishlistService
        .addToWishlist(product.id)
        .subscribe(() => product.inWishlist = true);

    }

  }

  handleCart(product: ProductListResponse): void {

    console.log("Cart clicked:", product);

    this.cartService.addItemToCart({

      productVariantId: product.productVariantId,

      quantity: 1

    });

  }

  openProduct(id: number): void {

    this.router.navigate([
      '/publicProductView',
      id
    ]);

  }

  writeReview(id: number): void {

    this.router.navigate([
      '/customer/review',
      id
    ]);

  }

}