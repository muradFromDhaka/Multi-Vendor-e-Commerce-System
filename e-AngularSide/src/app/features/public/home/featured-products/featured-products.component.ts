import { Component, OnInit } from '@angular/core';

import { ProductListResponse } from 'src/app/models/product.model';
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
    private productService: ProductService
  ) {}



  ngOnInit(): void {

    this.loadFeaturedProducts();

  }



  loadFeaturedProducts(): void {


    this.loading = true;


    this.productService
        .getLatest()
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



}