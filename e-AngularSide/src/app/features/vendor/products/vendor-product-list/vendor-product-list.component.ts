import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductDetailsResponse, ProductListResponse } from 'src/app/models/product.model';
import { VendorProductService } from '../../services/vendor-product.service';

@Component({
  selector: 'app-vendor-product-list',
  templateUrl: './vendor-product-list.component.html',
  styleUrls: ['./vendor-product-list.component.scss']
})
export class VendorProductListComponent implements OnInit {

  products: ProductListResponse[] = [];

  loading = false;

  page = 0;
  size = 10;

  totalPages = 0;
  totalElements = 0;

  searchKeyword = '';

  constructor(
    private vendorProductService: VendorProductService,
    private acRouter: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.acRouter.queryParams.subscribe(params => {

    const keyword = params['keyword'];

    if (keyword) {
      this.searchKeyword = keyword;
      this.search();
    } else {
      this.loadProducts();
    }

  });

}

  loadProducts(): void {

    this.loading = true;

    this.vendorProductService
      .getMyProducts(this.page, this.size)
      .subscribe({

        next: (res) => {

          this.products = res.content;

          this.page = res.number;
          this.size = res.size;
          this.totalPages = res.totalPages;
          this.totalElements = res.totalElements;

          this.loading = false;
        },

        error: (err) => {

          console.error(err);
          this.loading = false;

        }

      });

  }


search(): void {

  this.loading = true;

  this.vendorProductService
      .searchProducts(
          this.searchKeyword,
          this.page,
          this.size
      )
      .subscribe({

        next: res => {

          this.products = res.content;
          this.page = res.number;
          this.totalPages = res.totalPages;
          this.totalElements = res.totalElements;

          this.loading = false;
        },

        error: err => {

          console.error(err);
          this.loading = false;

        }

      });

}


  editProduct(id: number): void {

    this.router.navigate(['/vendor/productForm', id]);

  }

  viewProduct(id: number): void {

    this.router.navigate(['/vendor/productDetails', id]);

  }

  deleteProduct(id: number): void {

    if (!confirm('Are you sure to delete this product?')) {
      return;
    }

    this.vendorProductService
      .deleteProduct(id)
      .subscribe(() => {

        this.loadProducts();

      });

  }

  changePage(page: number): void {

  if (
    page < 0 ||
    page >= this.totalPages ||
    page === this.page
  ) {
    return;
  }

  this.page = page;

  if (this.searchKeyword.trim()) {
    this.search();
  } else {
    this.loadProducts();
  }

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

  get pages(): (number | string)[] {

    const pages: (number | string)[] = [];

    if (this.totalPages <= 7) {

      for (let i = 0; i < this.totalPages; i++) {
        pages.push(i);
      }

      return pages;
    }

    if (this.page <= 3) {

      pages.push(
        0,
        1,
        2,
        3,
        4,
        '...',
        this.totalPages - 1
      );

      return pages;

    }

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

}