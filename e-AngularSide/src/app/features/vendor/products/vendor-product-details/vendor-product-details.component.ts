import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductDetailsResponse } from 'src/app/models/product.model';
import { environment } from 'src/app/services/environments';
import { VendorProductService } from '../../services/vendor-product.service';

@Component({
  selector: 'app-vendor-product-details',
  templateUrl: './vendor-product-details.component.html',
  styleUrls: ['./vendor-product-details.component.scss']
})
export class VendorProductDetailsComponent implements OnInit {

  productId!: number;

  product?: ProductDetailsResponse;

  loading = false;

  selectedImage = 0;

  readonly baseImageUrl = environment.baseImageUrl;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private vendorProductService: VendorProductService
  ) {}

  ngOnInit(): void {

    this.route.paramMap.subscribe(params => {

      const id = params.get('id');

      if (id) {

        this.productId = +id;

        this.loadProduct();

      }

    });

  }

  loadProduct(): void {

    this.loading = true;

    this.vendorProductService
      .getProductById(this.productId)
      .subscribe({

        next: (res) => {

          this.product = res;

          this.loading = false;

        },

        error: (err) => {

          console.error(err);

          this.loading = false;

        }

      });

  }

  changeImage(index: number): void {

    this.selectedImage = index;

  }

  editProduct(): void {

    this.router.navigate([
      '/vendor/productForm',
      this.productId
    ]);

  }

  addVariant(): void {

    this.router.navigate([
      '/vendor/productVariants/create',
      this.productId
    ]);

  }

}