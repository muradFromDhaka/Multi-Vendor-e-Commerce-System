import { Component } from '@angular/core';


interface Feature {

  icon:string;

  title:string;

  description:string;

}



@Component({
  selector: 'app-why-choose-us',
  templateUrl: './why-choose-us.component.html',
  styleUrls: ['./why-choose-us.component.scss']
})
export class WhyChooseUsComponent {



features:Feature[] = [


  {
    icon:'bi-truck',
    title:'Fast Delivery',
    description:
    'Quick and reliable delivery service across the country.'
  },


  {
    icon:'bi-shield-check',
    title:'Secure Payment',
    description:
    'Your payment information is protected with secure technology.'
  },


  {
    icon:'bi-award',
    title:'Quality Products',
    description:
    'We provide verified and premium quality products.'
  },


  {
    icon:'bi-headset',
    title:'24/7 Support',
    description:
    'Our support team is always ready to help you.'
  }


];



}