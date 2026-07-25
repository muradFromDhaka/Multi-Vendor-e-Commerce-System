import { Component } from '@angular/core';

@Component({
  selector: 'app-hero-banner',
  templateUrl: './hero-banner.component.html',
  styleUrls: ['./hero-banner.component.scss']
})
export class HeroBannerComponent {


  banners = [

    {
      title: 'Discover Premium Products',
      subtitle:
      'Shop quality products from trusted vendors',
      buttonText:'Shop Now',
      image:
      'assets/images/banner-1.jpg'
    },


    {
      title:'New Fashion Collection',
      subtitle:
      'Upgrade your style with latest trends',
      buttonText:'Explore Now',
      image:
      'assets/images/banner-2.jpg'
    }

  ];


  currentBanner = 0;



  nextBanner(){

    this.currentBanner++;

    if(this.currentBanner >= this.banners.length){

      this.currentBanner = 0;

    }

  }



  previousBanner(){

    this.currentBanner--;

    if(this.currentBanner < 0){

      this.currentBanner =
      this.banners.length - 1;

    }

  }

}