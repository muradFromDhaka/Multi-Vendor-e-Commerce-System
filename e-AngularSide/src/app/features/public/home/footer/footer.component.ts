import { Component } from '@angular/core';


@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent {



quickLinks = [

  'Home',
  'Products',
  'Categories',
  'Wishlist',
  'My Account'

];



customerLinks = [

  'Help Center',
  'Shipping Policy',
  'Return Policy',
  'Privacy Policy'

];



socialLinks = [

  {
    icon:'bi-facebook',
    link:'#'
  },

  {
    icon:'bi-instagram',
    link:'#'
  },

  {
    icon:'bi-youtube',
    link:'#'
  }

];



}