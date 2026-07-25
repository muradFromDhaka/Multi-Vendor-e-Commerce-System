import { Component, OnInit } from '@angular/core';

import { ProductListResponse } from 'src/app/models/product.model';
import { ProductService } from 'src/app/services/product.service';


@Component({
  selector: 'app-new-arrivals',
  templateUrl: './new-arrivals.component.html',
  styleUrls: ['./new-arrivals.component.scss']
})
export class NewArrivalsComponent implements OnInit {


  products: ProductListResponse[] = [];

  loading = true;



  constructor(
    private productService: ProductService
  ){}



  ngOnInit(): void {

    this.loadNewArrivals();

  }




  loadNewArrivals(): void {


    this.loading = true;



    this.productService
        .getLatest()
        .subscribe({

          next:(res)=>{


            this.products = res.content.slice(0,8);


            this.loading = false;


          },


          error:(err)=>{


            console.error(
              "Failed to load new arrivals",
              err
            );


            this.loading = false;


          }


        });



  }



}