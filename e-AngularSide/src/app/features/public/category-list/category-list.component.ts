import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/app/services/environments';
import { CategoryService } from 'src/app/services/category.service';
import { CategoryResponse } from 'src/app/models/category.model';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.scss']
})
export class CategoryListComponent implements OnInit {

  categories: CategoryResponse[] = [];
  loading = false;

  baseImageUrl = environment.baseImageUrl;

  constructor(
    private categoryService: CategoryService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {

    this.loading = true;

    this.categoryService.getAllCategories().subscribe({

      next: (res) => {
        this.categories = res.content;
        this.loading = false;
      },

      error: (err) => {
        console.error(err);
        this.loading = false;
      }

    });

  }

  openCategory(category: CategoryResponse): void {

    this.router.navigate([
      '/categoryProduct',
      category.id,
      category.name
    ]);

  }

}