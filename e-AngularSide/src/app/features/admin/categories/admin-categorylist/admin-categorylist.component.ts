import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { BrandResponse } from 'src/app/models/brand.model';
import { CategoryResponse } from 'src/app/models/category.model';
import { AuthService } from 'src/app/services/auth.service';
import { BrandService } from 'src/app/services/brand.service';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-admin-categorylist',
  templateUrl: './admin-categorylist.component.html',
  styleUrls: ['./admin-categorylist.component.scss'],
})
export class AdminCategorylistComponent {
  categories: CategoryResponse[] = [];
  loading = false;
  searchTerm = '';
  brand: BrandResponse[] = [];
  constructor(
    private categoryService: CategoryService,
    public authService: AuthService,
    private brandService: BrandService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadCategories();
    this.brandService.getAll().subscribe((res) => this.brand = res);
  }

  loadCategories() {
    this.loading = true;
    this.categoryService.getAllCategories().subscribe({
      next: (res) => {
        this.categories = res.content;
        this.loading = false;
      },
      error: () => (this.loading = false),
    });
  }

  addCategory() {
    this.router.navigate(['/categories/add']);
  }

  editCategory(id: number) {
    this.router.navigate(['/categories/edit', id]);
  }

  deleteCategory(id: number) {
    if (!confirm('Are you sure to delete this category?')) return;
    this.categoryService
      .deleteCategory(id)
      .subscribe(() => this.loadCategories());
  }
}
