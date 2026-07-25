import { Component, OnInit } from '@angular/core';

import { ProductListResponse } from 'src/app/models/product.model';
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
    private productService: ProductService
  ){}




  ngOnInit(): void {

    this.loadDeals();

  }





  loadDeals(){


    /*
      Future:
      productService.getDiscountedProducts()
    */


    this.productService
        .getLatest()
        .subscribe({

          next:(res)=>{


            this.deals = res.content
              .filter(product =>
                product.discountPrice
              )
              .slice(0,4);



            this.loading=false;


          },


          error:(err)=>{


            console.error(
              "Failed to load deals",
              err
            );


            this.loading=false;


          }


        });


  }



}