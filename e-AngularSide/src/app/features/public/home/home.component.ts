import { Component, OnDestroy, OnInit } from '@angular/core';
import { environment } from 'src/app/services/environments';
import { BrandResponse } from 'src/app/models/brand.model';
import { CategoryResponse } from 'src/app/models/category.model';
import { BrandService } from 'src/app/services/brand.service';
import { CartService } from 'src/app/services/cart.service';
import { CategoryService } from 'src/app/services/category.service';
import { ProductService } from 'src/app/services/product.service';
import { ProductListResponse } from 'src/app/models/product.model';
import { SearchService } from 'src/app/shared/services/search.service';
import { Router } from '@angular/router';
import { WishlistService } from '../../customer/services/wishlist.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
//  implements OnInit,OnDestroy

//     categories: CategoryResponse[] = [];
//     products: ProductListResponse[] = [];
//     mostPopularProducts: ProductListResponse[] = [];
//     topBrands: BrandResponse[] = [];
     
//     baseImageUrl = environment.baseImageUrl;
  
//      currentIndex = 0;
//     intervalId!: any;
    
//     searchQuery = '';
  
//   constructor(
//     private categoryService:CategoryService,
//     private wishlistService: WishlistService,
//     private productService:ProductService,
//     private brandService:BrandService,
//     private cartService:CartService,
//     private router: Router
//   ){}
  
  
//      ngOnInit(): void {
//       this.loadProducts();
//       this.loadCategory();
//       this.loadBrand();
//     }
  
//     loadProducts():void{
//       this.productService.getAllProducts().subscribe({
//         next: 
//         (res) => {this.products = res.content
//                   this.checkWishlistStatus();
//         }
      
//       })
//       this.productService.getMostPopular().subscribe((res) => this.mostPopularProducts = res.content)
//     }
  
//     loadCategory():void {
//       this.categoryService.getAllCategories()
//         .subscribe(res => {
//            this.categories = res.content;
      
//       if (this.categories.length > 0) {
//         this.startAutoSlide();
//       }
//     });
// }
  
//     loadBrand():void{
//       this.brandService.getAll().subscribe((res) => this.topBrands = res)
//     }
  
  
//     ngOnDestroy(): void {
//       clearInterval(this.intervalId);
//     }
  
//     startAutoSlide() {
//       this.intervalId = setInterval(() => {
//         this.nextSlide();
//       }, 3000);
//     }
  
//     nextSlide() {
//      if (!this.categories.length) {
//      return;
//     }
//       this.currentIndex =
//       (this.currentIndex + 1) % this.categories.length;
// }
  
//     goToSlide(index: number) {
//       this.currentIndex = index;
//     }
  
// onSearch(): void {

//   const keyword = this.searchQuery.trim();

//   if (!keyword) {
//     return;
//   }

//   this.router.navigate(
//     ['/publicProductList'],
//     {
//       queryParams: {
//         keyword: keyword
//       }
//     }
//   );

// }
  
//     addToCart(product: ProductListResponse): void {
          
//     this.cartService.addItemToCart({
//       productVariantId: product.productVariantId,
//       quantity: 1
//     });
//   }

//     quickView(product: ProductListResponse) {
//       console.log('Quick view:', product);
//     }


//     getImageUrl(product: ProductListResponse): string {

//   if (!product.thumbnailUrl) {

//     return 'assets/images/no-image.png';

//   }

//   return product.thumbnailUrl.startsWith('http')

//     ? product.thumbnailUrl

//     : this.baseImageUrl + product.thumbnailUrl;

// }

// // ====================================

// checkWishlistStatus(){

//   this.products.forEach(product => {
    
//     this.wishlistService
//         .existsInWishlist(product.id)
//         .subscribe({

//           next:(exists)=>{

//             product.inWishlist = exists;

//           },

//           error:(err)=>{

//             console.log(err);

//           }

//         });


//   });

// }



// toggleWishlist(product: ProductListResponse){


//   if(product.inWishlist){


//     this.wishlistService
//         .removeFromWishlist(product.id)
//         .subscribe({

//           next:()=>{

//             product.inWishlist = false;

//           }

//         });



//   }else{


//     this.wishlistService
//         .addToWishlist(product.id)
//         .subscribe({

//           next:()=>{

//             product.inWishlist = true;

//           }

//         });

//   }


// }


}
