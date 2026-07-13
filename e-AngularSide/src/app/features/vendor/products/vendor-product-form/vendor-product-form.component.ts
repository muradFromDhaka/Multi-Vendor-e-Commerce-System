import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators
} from '@angular/forms';
import { Router } from '@angular/router';

import {
  ProductRequest,
  ProductStatus
} from 'src/app/models/product.model';
import { VendorProductService } from '../../services/vendor-product.service';


@Component({
  selector: 'app-vendor-product-form',
  templateUrl: './vendor-product-form.component.html',
  styleUrls: ['./vendor-product-form.component.scss']
})
export class VendorProductFormComponent implements OnInit {

  productForm!: FormGroup;

  selectedImages: File[] = [];

  loading = false;

  ProductStatus = ProductStatus;

  statuses = Object.values(ProductStatus);

  // Demo Data
  categories = [
    { id: 1, name: 'Men Fashion' },
    { id: 2, name: 'Women Fashion' },
    { id: 3, name: 'Electronics' }
  ];

  brands = [
    { id: 1, name: 'Nike' },
    { id: 2, name: 'Adidas' },
    { id: 3, name: 'Apple' }
  ];

  constructor(
    private fb: FormBuilder,
    private vendorProductService: VendorProductService,
    private router: Router
  ) {}

  ngOnInit(): void {

    this.productForm = this.fb.group({

      name: [
        '',
        [
          Validators.required,
          Validators.minLength(3)
        ]
      ],

      description: [''],

      categoryId: [
        null,
        Validators.required
      ],

      brandId: [
        null,
        Validators.required
      ],

      status: [
        ProductStatus.ACTIVE,
        Validators.required
      ],

      releaseDate: ['']
    });
  }

  onFileSelect(event: Event): void {

    const input = event.target as HTMLInputElement;

    if (!input.files) return;

    this.selectedImages = Array.from(input.files);
  }

  removeImage(index: number): void {

    this.selectedImages.splice(index, 1);
  }

  submit(): void {

    if (this.productForm.invalid) {

      this.productForm.markAllAsTouched();
      return;
    }

    this.loading = true;

    const product: ProductRequest = {
      ...this.productForm.value
    };

    this.vendorProductService
      .createProduct(
        product,
        this.selectedImages
      )
      .subscribe({

        next: (res) => {

          console.log(res);

          alert('Product Created Successfully');

          this.router.navigate([
            '/vendor/products'
          ]);
        },

        error: (err) => {

          console.error(err);

          alert(
            'Failed To Create Product'
          );

          this.loading = false;
        },

        complete: () => {
          this.loading = false;
        }
      });
  }
}