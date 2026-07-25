import { Component, OnInit } from '@angular/core';


interface TopVendor {

  id:number;

  shopName:string;

  logoUrl:string;

  rating:number;

  totalProducts:number;

}


@Component({
  selector: 'app-top-vendors',
  templateUrl: './top-vendors.component.html',
  styleUrls: ['./top-vendors.component.scss']
})
export class TopVendorsComponent implements OnInit {


  vendors: TopVendor[] = [];

  loading = true;



  ngOnInit(): void {

    this.loadTopVendors();

  }




  loadTopVendors(){


    // Temporary demo data
    // Later replace with vendor API


    this.vendors = [


      {
        id:1,
        shopName:'Premium Fashion BD',
        logoUrl:'assets/images/vendor1.jpg',
        rating:4.8,
        totalProducts:120
      },


      {
        id:2,
        shopName:'Tech World',
        logoUrl:'assets/images/vendor2.jpg',
        rating:4.6,
        totalProducts:85
      },


      {
        id:3,
        shopName:'Smart Lifestyle',
        logoUrl:'assets/images/vendor3.jpg',
        rating:4.9,
        totalProducts:200
      },


      {
        id:4,
        shopName:'Urban Collection',
        logoUrl:'assets/images/vendor4.jpg',
        rating:4.5,
        totalProducts:60
      }


    ];


    this.loading=false;


  }



}