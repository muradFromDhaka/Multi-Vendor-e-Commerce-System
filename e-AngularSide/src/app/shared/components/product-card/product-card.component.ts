import { Component, Input } from '@angular/core';
import { ProductListResponse } from 'src/app/models/product.model';
import { environment } from 'src/app/services/environments';
import { CartService } from 'src/app/services/cart.service';
import { WishlistService } from 'src/app/features/customer/services/wishlist.service';


@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.scss']
})
export class ProductCardComponent {


  @Input()
  product!: ProductListResponse;


  baseImageUrl = environment.baseImageUrl;



  constructor(
    private cartService: CartService,
    private wishlistService: WishlistService
  ){}



  addToCart(){


    this.cartService.addItemToCart({

      productVariantId:
      this.product.productVariantId,

      quantity:1

    });


  }




  toggleWishlist(){


    if(this.product.inWishlist){


      this.wishlistService
      .removeFromWishlist(this.product.id)
      .subscribe(()=>{

        this.product.inWishlist=false;

      });



    }else{


      this.wishlistService
      .addToWishlist(this.product.id)
      .subscribe(()=>{

        this.product.inWishlist=true;

      });


    }


  }




  getImageUrl():string{


    if(!this.product.thumbnailUrl){

      return 'assets/images/no-image.png';

    }


    return this.product.thumbnailUrl.startsWith('http')
    ?
    this.product.thumbnailUrl
    :
    this.baseImageUrl + this.product.thumbnailUrl;


  }


}