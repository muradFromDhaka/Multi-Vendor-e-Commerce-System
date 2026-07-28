import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { CategoryResponse } from 'src/app/models/category.model';
import { CategoryService } from 'src/app/services/category.service';
import { environment } from 'src/app/services/environments';


@Component({
  selector: 'app-category-section',
  templateUrl: './category-section.component.html',
  styleUrls: ['./category-section.component.scss']
})
export class CategorySectionComponent implements OnInit {


  categories: CategoryResponse[] = [];

  loading = true;

  baseImageUrl = environment.baseImageUrl;



  constructor(
    private categoryService: CategoryService,
    private router: Router
  ){}



  ngOnInit(): void {

    this.loadCategories();

  }




  loadCategories(){


    this.categoryService
        .getAllCategories()
        .subscribe({

          next:(res)=>{


            this.categories = res.content;


            this.loading=false;


          },


          error:(err)=>{


            console.error(
              "Failed to load categories",
              err
            );


            this.loading=false;


          }


        });


  }





  getImageUrl(category:CategoryResponse){


    if(!category.imageUrl){

      return 'assets/images/no-image.png';

    }

    // console.log("category", this.baseImageUrl + category.imageUrl)

    return category.imageUrl.startsWith('http')
         ?
         this.baseImageUrl + category.imageUrl : this.baseImageUrl + category.imageUrl;


  }




  openCategory(categoryId:number){


    this.router.navigate(
      ['/category',categoryId]
    );


  }


}