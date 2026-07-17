import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProductListResponse } from 'src/app/models/product.model';
import { AuthService } from 'src/app/services/auth.service';
import { ProductService } from 'src/app/services/product.service';
import { AdminProductService } from '../../services/admin-product.service';

@Component({
  selector: 'app-admin-productlist',
  templateUrl: './admin-productlist.component.html',
  styleUrls: ['./admin-productlist.component.scss']
})
export class AdminProductlistComponent implements OnInit {

  page = 0;
  size = 10;
  totalPages = 0;
  pageNumbers: number[] = [];

  products: ProductListResponse[] = [];
  loading = false;

  constructor(
    private adminProductService: AdminProductService,
    public authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {

  this.loading = true;

  this.adminProductService
    .getAllProducts(this.page, this.size)
    .subscribe({

      next: (res) => {

        this.products = res.content;

        console.log(this.products);

        this.page = res.number;
        this.totalPages = res.totalPages;

        this.pageNumbers = Array.from(
          { length: this.totalPages },
          (_, i) => i
        );

        this.loading = false;
      },

      error: () => this.loading = false

    });

}

  editProduct(id: number): void {
    this.router.navigate(['/admin/adminEditProductForm', id]);
  }

  viewProduct(id: number): void {
    this.router.navigate(['/admin/adminProductView', id]);
  }

  deleteProduct(id: number): void {

    if (!confirm('Are you sure to delete this product?')) {
      return;
    }

    this.adminProductService.deleteProduct(id).subscribe(() => {
      this.loadProducts();
    });

  }
  

get pages(): (number | string)[] {

  const pages: (number | string)[] = [];

  if (this.totalPages <= 7) {

    for (let i = 0; i < this.totalPages; i++) {
      pages.push(i);
    }

    return pages;
  }

  // First Pages
  if (this.page <= 3) {

    pages.push(0, 1, 2, 3, 4, '...', this.totalPages - 1);

    return pages;
  }

  // Last Pages
  if (this.page >= this.totalPages - 4) {

    pages.push(
      0,
      '...',
      this.totalPages - 5,
      this.totalPages - 4,
      this.totalPages - 3,
      this.totalPages - 2,
      this.totalPages - 1
    );

    return pages;
  }

  // Middle
  pages.push(
    0,
    '...',
    this.page - 1,
    this.page,
    this.page + 1,
    '...',
    this.totalPages - 1
  );

  return pages;
}

changePage(page: number): void {

  if (page < 0 || page >= this.totalPages || page === this.page) {
    return;
  }

  this.page = page;
  this.loadProducts();
}

nextPage(): void {

  if (this.page < this.totalPages - 1) {
    this.changePage(this.page + 1);
  }

}

previousPage(): void {

  if (this.page > 0) {
    this.changePage(this.page - 1);
  }

}

}