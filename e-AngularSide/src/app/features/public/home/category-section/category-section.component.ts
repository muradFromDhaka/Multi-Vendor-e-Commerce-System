import { Component } from '@angular/core';

@Component({
  selector: 'app-category-section',
  templateUrl: './category-section.component.html',
  styleUrls: ['./category-section.component.scss']
})
export class CategorySectionComponent {


categories = [

  {
    name:'Fashion',
    image:'assets/images/categories/fashion.jpg'
  },


  {
    name:'Electronics',
    image:'assets/images/categories/electronics.jpg'
  },


  {
    name:'Home & Living',
    image:'assets/images/categories/home.jpg'
  },


  {
    name:'Beauty',
    image:'assets/images/categories/beauty.jpg'
  },


  {
    name:'Sports',
    image:'assets/images/categories/sports.jpg'
  },


  {
    name:'Accessories',
    image:'assets/images/categories/accessories.jpg'
  }


];


}