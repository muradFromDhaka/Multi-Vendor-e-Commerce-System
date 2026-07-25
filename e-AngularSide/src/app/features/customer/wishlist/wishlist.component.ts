import { Component, OnInit } from '@angular/core';
import { WishlistResponse } from '../models/wishlist.model';
import { WishlistService } from '../services/wishlist.service';

@Component({
  selector:'app-wishlist',
  templateUrl:'./wishlist.component.html',
  styleUrls:['./wishlist.component.scss']
})
export class WishlistComponent implements OnInit {


  wishlist!: WishlistResponse;


  loading = true;


  constructor(
    private wishlistService: WishlistService
  ){}



  ngOnInit(): void {

    this.loadWishlist();

  }



  loadWishlist(){

    this.wishlistService.getWishlist()
    .subscribe({

      next:(res)=>{

        this.wishlist = res;

        this.loading = false;

      },

      error:(err)=>{

        console.log(err);

        this.loading=false;

      }

    })

  }



  removeProduct(productId:number){

    this.wishlistService
    .removeFromWishlist(productId)
    .subscribe({

      next:(res)=>{

        this.wishlist = res;

      }

    })

  }

}