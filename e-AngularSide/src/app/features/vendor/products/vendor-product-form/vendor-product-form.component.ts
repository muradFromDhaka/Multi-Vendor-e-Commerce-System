import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators
} from '@angular/forms';

import {
  ActivatedRoute,
  Router
} from '@angular/router';

import {
  ProductRequest,
  ProductStatus
} from 'src/app/models/product.model';

import { VendorProductService } from '../../services/vendor-product.service';
import { CategoryResponse } from 'src/app/models/category.model';
import { BrandResponse } from 'src/app/models/brand.model';
import { BrandService } from 'src/app/services/brand.service';
import { CategoryService } from 'src/app/services/category.service';
import { environment } from 'src/app/services/environments';

@Component({
  selector: 'app-vendor-product-form',
  templateUrl: './vendor-product-form.component.html',
  styleUrls: ['./vendor-product-form.component.scss']
})
export class VendorProductFormComponent implements OnInit {

  productForm!: FormGroup;

  baseImageUrl = environment.baseImageUrl;

  loading = false;

  isEdit = false;

  productId!: number;

  ProductStatus = ProductStatus;

  statuses = Object.values(ProductStatus);

  selectedImages: File[] = [];

  newImagePreview: string[] = [];

  existingImages: string[] = [];

  categories: CategoryResponse[] =[];

  brands: BrandResponse[] = [];

  constructor(
    private fb: FormBuilder,
    private brandService: BrandService,
    private categoryService: CategoryService,
    private vendorProductService: VendorProductService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {

    this.initializeForm();

    this.loadBrand();

    this.loadCategory();

    this.checkEditMode();

  }

  private initializeForm(): void {

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

  private checkEditMode(): void {

    this.route.paramMap.subscribe(params => {

      const id = params.get('id');

      if (id) {

        this.isEdit = true;

        this.productId = +id;

        this.loadProduct();

      }

    });

  }

  loadBrand():void {
     this.brandService.getAll()
     .subscribe({
      next:(res)=> {
        this.brands = res;
      },

      error: (err)=>{
        console.log('Faild to load brands: ', err);
      }
      
     })
  }

  loadCategory(): void {
    this.categoryService.getAllCategories()
    .subscribe({
      next:(res)=>{

        this.categories = res.content

      },

      error: err => {
        console.log("Faild to load Categories: ", err);
      }
    })
  }

  private loadProduct(): void {

    this.vendorProductService
      .getProductById(this.productId)
      .subscribe({

        next: (product) => {

          this.productForm.patchValue({

            name: product.name,

            description: product.description,

            categoryId: product.categoryId,

            brandId: product.brandId,

            status: product.status,

            releaseDate: product.releaseDate

          });

          this.existingImages = [...product.imageUrls];

        },

        error: err => {

          console.error(err);

          alert('Failed to load product.');

        }

      });

  }

  onFileSelect(event: Event): void {

    const input = event.target as HTMLInputElement;

    if (!input.files) {
      return;
    }

    Array.from(input.files).forEach(file => {

      this.selectedImages.push(file);

      const reader = new FileReader();

      reader.onload = () => {

        this.newImagePreview.push(
          reader.result as string
        );

      };

      reader.readAsDataURL(file);

    });

  }

  removeExistingImage(index: number): void {

    this.existingImages.splice(index, 1);

  }

  removeNewImage(index: number): void {

    this.selectedImages.splice(index, 1);

    this.newImagePreview.splice(index, 1);

  }

  submit(): void {

    if (this.productForm.invalid) {

      this.productForm.markAllAsTouched();

      return;

    }

    const dto: ProductRequest = {

      ...this.productForm.value,

      imageUrls: this.existingImages

    };

    this.loading = true;

    if (this.isEdit) {

      this.vendorProductService
        .updateProduct(
          this.productId,
          dto,
          this.selectedImages
        )
        .subscribe({

          next: () => {

            alert('Product Updated Successfully');

            this.router.navigate([
              '/vendor/productList'
            ]);

          },

          error: err => {

            console.error(err);

            alert('Failed to update product.');

            this.loading = false;

          },

          complete: () => {

            this.loading = false;

          }

        });

    }

    else {

      this.vendorProductService
        .createProduct(
          dto,
          this.selectedImages
        )
        .subscribe({

          next: () => {

            alert('Product Created Successfully');

            this.router.navigate([
              '/vendor/productList'
            ]);

          },

          error: err => {

            console.error(err);

            alert('Failed to create product.');

            this.loading = false;

          },

          complete: () => {

            this.loading = false;

          }

        });

    }

  }

}