import { Component, OnDestroy, OnInit } from '@angular/core';


@Component({
  selector: 'app-flash-sale',
  templateUrl: './flash-sale.component.html',
  styleUrls: ['./flash-sale.component.scss']
})
export class FlashSaleComponent 
implements OnInit, OnDestroy {


  hours = 2;
  minutes = 30;
  seconds = 45;


  timer!: any;



  products = [

    {
      id:1,
      name:'Premium T-Shirt',
      image:'assets/images/product-1.jpg',
      price:1200,
      discountPrice:799
    },


    {
      id:2,
      name:'Running Shoes',
      image:'assets/images/product-2.jpg',
      price:2500,
      discountPrice:1599
    },


    {
      id:3,
      name:'Smart Watch',
      image:'assets/images/product-3.jpg',
      price:5000,
      discountPrice:3499
    }

  ];




  ngOnInit(): void {

    this.startTimer();

  }




  startTimer(){

    this.timer = setInterval(()=>{


      if(this.seconds > 0){

        this.seconds--;

      }

      else if(this.minutes > 0){

        this.minutes--;

        this.seconds = 59;

      }

      else if(this.hours > 0){

        this.hours--;

        this.minutes = 59;

        this.seconds = 59;

      }


    },1000);


  }




  ngOnDestroy(): void {

    clearInterval(this.timer);

  }



}