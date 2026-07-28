import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { WishlistService } from 'src/app/features/customer/services/wishlist.service';

import { ProductListResponse } from 'src/app/models/product.model';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-deals-section',
  templateUrl: './deals-section.component.html',
  styleUrls: ['./deals-section.component.scss']
})
export class DealsSectionComponent implements OnInit {

  deals: ProductListResponse[] = [];

  loading = true;

  constructor(
    private productService: ProductService,
    private wishlistService: WishlistService,
    private authService: AuthService,
    private cartService: CartService,
    private router: Router
  ) {}

  ngOnInit(): void {

    console.log("Deals component loaded");
    
    this.loadDeals();

  }

  loadDeals(): void {

    this.loading = true;

    this.productService
      .getDeals()
      .subscribe({

        next: (res) => {

          this.deals = res.content;

          console.log("Deals:-----------------", this.deals)

          this.loading = false;

        },

        error: (err) => {

          console.error(
            'Failed to load deals',
            err
          );

          this.loading = false;

        }

      });

  }



  handleWishlist(product: ProductListResponse){


  if(!this.authService.isLoggedIn()){

      this.router.navigate(['/auth/login']);

      return;

  }


  if(product.inWishlist){

    this.wishlistService
    .removeFromWishlist(product.id)
    .subscribe({

      next:()=>{

        product.inWishlist = false;

      }

    });

  }
  else{

    this.wishlistService
    .addToWishlist(product.id)
    .subscribe({

      next:()=>{

        product.inWishlist = true;

      }

    });

  }

}



handleCart(product: ProductListResponse){


this.cartService.addItemToCart({

productVariantId:
product.productVariantId,

quantity:1

});


}



openProduct(id:number){


this.router.navigate([
'/publicProductView',
id
]);


}



writeReview(id:number){


this.router.navigate([
'/customer/review',
id
]);


}


}