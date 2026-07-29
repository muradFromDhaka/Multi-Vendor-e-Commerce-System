import { Component, OnInit } from '@angular/core';

import { ProductListResponse } from 'src/app/models/product.model';
import { ProductService } from 'src/app/services/product.service';


@Component({
  selector: 'app-top-rated-products',
  templateUrl: './top-rated-products.component.html',
  styleUrls: ['./top-rated-products.component.scss']
})
export class TopRatedProductsComponent implements OnInit {


  products: ProductListResponse[] = [];

  loading = true;



  constructor(
    private productService: ProductService
  ){}



  ngOnInit(): void {

    this.loadTopRatedProducts();

  }




  loadTopRatedProducts(): void {


    this.loading = true;


    /*
      Future:
      this.productService.getTopRatedProducts()
    */


    this.productService
        .getTopRated()
        .subscribe({

          next:(res)=>{

            // console.log("Response:", res.content);

            this.products = res.content
              .filter(product =>
                (product.averageRating ?? 0) >= 2
              )
              .slice(0,8);

 console.log("All Products:", res.content);

  res.content.forEach(product => {
    console.log(product.name, product.averageRating);
  });
            this.loading=false;


          },


          error:(err)=>{


            console.error(
              "Failed to load top rated products",
              err
            );


            this.loading=false;


          }


        });


  }



}