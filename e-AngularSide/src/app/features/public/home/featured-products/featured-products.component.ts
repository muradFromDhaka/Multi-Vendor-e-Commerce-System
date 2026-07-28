import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { WishlistService } from 'src/app/features/customer/services/wishlist.service';
import { ProductListResponse } from 'src/app/models/product.model';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';


@Component({
  selector: 'app-featured-products',
  templateUrl: './featured-products.component.html',
  styleUrls: ['./featured-products.component.scss']
})
export class FeaturedProductsComponent implements OnInit {

  products: ProductListResponse[] = [];

  loading = true;


 constructor(
  private productService: ProductService,
  private authService: AuthService,
  private cartService: CartService,
  private wishlistService: WishlistService,
  private router: Router
){}



  ngOnInit(): void {

    this.loadFeaturedProducts();

  }



  loadFeaturedProducts(): void {


    this.productService
        .getMostPopular(
          0,
          8
        )
        .subscribe({

          next:(res)=>{


            this.products = res.content;


            this.loading = false;


          },


          error:(err)=>{


            console.error(
              "Failed to load featured products",
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