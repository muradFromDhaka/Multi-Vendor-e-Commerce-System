import { Component } from '@angular/core';


interface CustomerReview {

  name:string;

  image:string;

  rating:number;

  comment:string;

}



@Component({
  selector: 'app-customer-reviews',
  templateUrl: './customer-reviews.component.html',
  styleUrls: ['./customer-reviews.component.scss']
})
export class CustomerReviewsComponent {



reviews:CustomerReview[]=[


{
 name:'Rahim Ahmed',
 image:'assets/images/customer1.jpg',
 rating:5,
 comment:
 'Excellent product quality and very fast delivery. Highly recommended.'
},



{
 name:'Nusrat Jahan',
 image:'assets/images/customer2.jpg',
 rating:4,
 comment:
 'Good shopping experience. Product was exactly as described.'
},



{
 name:'Sakib Hasan',
 image:'assets/images/customer3.jpg',
 rating:5,
 comment:
 'Amazing service and premium quality products.'
},



{
 name:'Farzana Akter',
 image:'assets/images/customer4.jpg',
 rating:5,
 comment:
 'Customer support was very helpful. Love this platform.'
}



];



}