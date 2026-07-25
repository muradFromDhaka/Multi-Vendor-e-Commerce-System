import { Component, OnInit } from '@angular/core';

import { ProductListResponse } from 'src/app/models/product.model';
import { ProductService } from 'src/app/services/product.service';


@Component({
  selector: 'app-best-selling-products',
  templateUrl: './best-selling-products.component.html',
  styleUrls: ['./best-selling-products.component.scss']
})
export class BestSellingProductsComponent implements OnInit {


  products: ProductListResponse[] = [];

  loading = true;



  constructor(
    private productService: ProductService
  ){}



  ngOnInit(): void {

    this.loadBestSellingProducts();

  }




  loadBestSellingProducts(): void {


    this.loading = true;


    /*
      Temporary:
      Later replace with:
      productService.getBestSellingProducts()
    */


    this.productService
        .getLatest()
        .subscribe({

          next:(res)=>{


            this.products = res.content.slice(0,8);


            this.loading=false;


          },


          error:(err)=>{


            console.error(
              "Failed to load best selling products",
              err
            );


            this.loading=false;


          }


        });


  }


}