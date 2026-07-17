import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductVariantService } from 'src/app/features/admin/services/product-variant.service';
import { ProductVariantResponse } from 'src/app/models/productVariant.model';
import { environment } from 'src/app/services/environments';
import { VendorProductVariantService } from '../../services/vendor-product-variant.service';

@Component({
  selector: 'app-vendor-product-variant-details',
  templateUrl: './vendor-product-variant-details.component.html',
  styleUrls: ['./vendor-product-variant-details.component.scss']
})
export class VendorProductVariantDetailsComponent {
 
  variant!: ProductVariantResponse;

  selectedImage = '';

  loading = false;

  readonly baseImageUrl = environment.baseImageUrl;

  constructor(

    private route: ActivatedRoute,

    private router: Router,

    private vendorProductVariantService: VendorProductVariantService

  ) {}

  ngOnInit(): void {

    const id = Number(
      this.route.snapshot.paramMap.get('id')
    );

    if (!id) {

      this.router.navigate([
        '/admin/productVariantList'
      ]);

      return;

    }

    this.loadVariant(id);

  }

  loadVariant(id: number): void {

    this.loading = true;

    this.vendorProductVariantService
      .getById(id)
      .subscribe({

        next: response => {

          this.variant = response;

          if (response.imageUrls?.length) {

         this.selectedImage =
         this.baseImageUrl + response.imageUrls[0];

        }

          this.loading = false;

        },

        error: err => {

          this.loading = false;

          console.error(err);

          alert(
            err.error?.message ||
            'Failed to load variant.'
          );

          this.router.navigate([
            '/admin/productVariantList'
          ]);

        }

      });

  }

  back(): void {

    this.router.navigate([
      '/vendor/vendorProductVariantList'
    ]);

  }

}