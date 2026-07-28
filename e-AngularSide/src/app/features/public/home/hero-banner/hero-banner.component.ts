import { Component, OnDestroy, OnInit } from '@angular/core';


@Component({
  selector: 'app-hero-banner',
  templateUrl: './hero-banner.component.html',
  styleUrls: ['./hero-banner.component.scss']
})
export class HeroBannerComponent implements OnInit, OnDestroy {



banners = [

{
 title:'Discover Premium Products',
 subtitle:'Shop quality products from trusted vendors',
 buttonText:'Shop Now',
 image:'assets/images/banner-1.jpg'
},


{
 title:'New Fashion Collection',
 subtitle:'Upgrade your style with latest trends',
 buttonText:'Explore Now',
 image:'assets/images/banner-2.jpg'
},


{
 title:'Exclusive Deals',
 subtitle:'Save more with amazing discounts',
 buttonText:'View Deals',
 image:'assets/images/banner-3.jpg'
}


];



currentBanner = 0;


interval:any;



ngOnInit():void{


this.startSlider();


}




startSlider(){


this.interval=setInterval(()=>{


this.nextBanner();


},5000);


}




nextBanner(){


this.currentBanner++;


if(this.currentBanner >= this.banners.length){

this.currentBanner=0;

}


}




previousBanner(){


this.currentBanner--;


if(this.currentBanner < 0){

this.currentBanner=this.banners.length-1;

}


}





goToBanner(index:number){


this.currentBanner=index;


}




ngOnDestroy():void{


clearInterval(this.interval);


}



}