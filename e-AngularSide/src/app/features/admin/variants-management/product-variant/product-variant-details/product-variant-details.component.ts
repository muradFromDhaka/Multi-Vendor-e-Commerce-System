import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ProductVariantResponse } from '../../../models/variants/productVariant.model';
import { ProductVariantService } from '../../../services/variants/product-variant.service';

import { environment } from 'src/app/services/environments';

@Component({
  selector: 'app-product-variant-details',
  templateUrl: './product-variant-details.component.html',
  styleUrls: ['./product-variant-details.component.scss']
})
export class ProductVariantDetailsComponent implements OnInit {

  variant!: ProductVariantResponse;

  selectedImage = '';

  loading = false;

  readonly baseImageUrl = environment.baseImageUrl;

  constructor(

    private route: ActivatedRoute,

    private router: Router,

    private variantService: ProductVariantService

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

    this.variantService
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
      '/admin/productVariantList'
    ]);

  }

}