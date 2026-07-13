import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AdminVendorService } from '../../services/admin-vendor.service';
import { environment } from 'src/app/services/environments';
import { ProductDetailsResponse } from 'src/app/models/product.model';

@Component({
  selector: 'app-vendor-products',
  templateUrl: './vendor-products.component.html',
  styleUrls: ['./vendor-products.component.scss']
})
export class VendorProductsComponent implements OnInit {

  vendorId!: number;

  products: ProductDetailsResponse[] = [];

  searchText = '';

  loading = false;

  filteredProducts: ProductDetailsResponse[] = [];

  page = 0;
  size = 10;

  totalPages = 0;
  totalElements = 0;

  constructor(
    private route: ActivatedRoute,
    private adminVendorService: AdminVendorService
  ) {}

  ngOnInit(): void {

    this.vendorId = Number(
      this.route.snapshot.paramMap.get('id')
    );

    this.loadProducts();

  }

loadProducts() {

  this.loading = true;

  this.adminVendorService
      .getVendorProducts(
        this.vendorId,
        this.page,
        this.size
      )
      .subscribe(res => {

        this.products = res.content;

        this.filteredProducts = [...this.products];

        this.totalPages = res.totalPages;

        this.totalElements = res.totalElements;

        this.filterProducts();

         this.loading = false;

      });

}


filterProducts() {

  const keyword = this.searchText.toLowerCase();

  this.filteredProducts = this.products.filter(p =>

      p.name.toLowerCase().includes(keyword)

  );

}


getImage(image?: string) {

  if (!image) {
    return 'assets/images/no-image.png';
  }

  const url = image.startsWith('http')
    ? image
    : `${environment.baseImageUrl}${image}`;

  console.log("Final URL:", url);

  return url;
}


  nextPage() {

  if (this.page < this.totalPages - 1) {

    this.page++;

    this.loadProducts();

  }

}


previousPage() {

  if (this.page > 0) {

    this.page--;

    this.loadProducts();

  }

}


getPrice(product: ProductDetailsResponse): number {

  return product.variants?.[0]?.price ?? 0;

}

getStock(product: ProductDetailsResponse): number {

  return product.variants?.[0]?.stock ?? 0;

}



getProductImage(product: ProductDetailsResponse): string {

  const image =
    product.variants?.[0]?.imageUrls?.[0] ||
    product.imageUrls?.[0];

  const finalUrl = image?.startsWith('http')
    ? image
    : `${environment.baseImageUrl}${image}`;

  console.log("Final URL =", finalUrl);

  return finalUrl;
}

}