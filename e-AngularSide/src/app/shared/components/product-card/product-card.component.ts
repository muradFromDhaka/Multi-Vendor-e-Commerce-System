import {
Component,
Input,
Output,
EventEmitter
} from '@angular/core';

import { ProductDetailsResponse, ProductListResponse } from
'src/app/models/product.model';

import { environment }
from 'src/app/services/environments';



@Component({

selector:'app-product-card',

templateUrl:'./product-card.component.html',

styleUrls:['./product-card.component.scss']

})

export class ProductCardComponent {



@Input()
product!: ProductListResponse;

 @Output()
  wishlist = new EventEmitter<ProductListResponse>();


  @Output()
  cart = new EventEmitter<ProductListResponse>();


  @Output()
  view = new EventEmitter<number>();


  @Output()
  review = new EventEmitter<number>();



baseImageUrl =
environment.baseImageUrl;




get discountPercentage(){


if(!this.product.discountPrice)
return 0;


return Math.round(

(
(this.product.price -
this.product.discountPrice)
/ this.product.price
)*100

);


}




getImageUrl(){


if(!this.product.thumbnailUrl){

return 'assets/images/no-image.png';

}


return this.product.thumbnailUrl.startsWith('http')

?
this.product.thumbnailUrl

:
this.baseImageUrl+
this.product.thumbnailUrl;


}






 toggleWishlist() {
    this.wishlist.emit(this.product);
  }




addToCart(){

console.log(
"Add cart",
this.product.id
);

this.cart.emit(this.product);

}




viewProduct(){

this.view.emit(
this.product.id
);

}




writeReview(){

this.review.emit(
this.product.id
);


}



}