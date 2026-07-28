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



features: Feature[] = [

  {
    icon: 'bi-patch-check-fill',
    title: 'Verified Sellers',
    description: 'Every seller is verified to ensure a safe and trusted shopping experience.'
  },

  {
    icon: 'bi-box-seam',
    title: 'Wide Product Selection',
    description: 'Explore thousands of products across multiple categories from trusted vendors.'
  },

  {
    icon: 'bi-truck',
    title: 'Fast & Reliable Delivery',
    description: 'Get your orders delivered quickly with reliable shipping partners.'
  },

  {
    icon: 'bi-shield-lock',
    title: 'Secure Payments',
    description: 'Enjoy safe and encrypted payment options for every purchase.'
  },

  {
    icon: 'bi-arrow-repeat',
    title: 'Easy Returns',
    description: 'Hassle-free return and refund process for eligible products.'
  },

  {
    icon: 'bi-tags',
    title: 'Daily Deals & Discounts',
    description: 'Save more with exclusive offers, flash sales, and special discounts.'
  },

  {
    icon: 'bi-chat-dots',
    title: 'Customer Reviews',
    description: 'Read genuine customer reviews to make confident buying decisions.'
  },

  {
    icon: 'bi-headset',
    title: 'Dedicated Support',
    description: 'Our customer support team is ready to assist you whenever you need help.'
  }

];


}