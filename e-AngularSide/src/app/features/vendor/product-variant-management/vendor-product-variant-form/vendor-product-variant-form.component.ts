import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DynamicAttribute } from 'src/app/models/variants/attribute.model';
import { ProductListResponse } from 'src/app/models/product.model';
import { ProductVariantRequest, ProductVariantResponse } from 'src/app/models/productVariant.model';
import { environment } from 'src/app/services/environments';
import { VendorProductVariantService } from '../../services/vendor-product-variant.service';
import { VendorProductService } from '../../services/vendor-product.service';
import { VendorAttributeService } from '../../services/variants/vendor-attribute.service';
import { VendorAttributeValueService } from '../../services/variants/vendor-attribute-value.service';

@Component({
  selector: 'app-vendor-product-variant-form',
  templateUrl: './vendor-product-variant-form.component.html',
  styleUrls: ['./vendor-product-variant-form.component.scss']
})
export class VendorProductVariantFormComponent {

  // ===============================
  // Reactive Form
  // ===============================

  form!: FormGroup;

  // ===============================
  // Dropdown Data
  // ===============================

  products: ProductListResponse[] = [];

  dynamicAttributes: DynamicAttribute[] = [];

  selectedCategoryId = 0;

  // ===============================
  // Image
  // ===============================

  selectedImages: File[] = [];

  existingImageUrls: string[] = [];

  imagePreviews: string[] = [];

  readonly baseImageUrl = environment.baseImageUrl;

  // ===============================
  // State
  // ===============================

  loading = false;

  isEditMode = false;

  variantId!: number;

  // ===============================
  // Constructor
  // ===============================

  constructor(

    private fb: FormBuilder,

    private router: Router,

    private route: ActivatedRoute,

    private vendorProductVariantService: VendorProductVariantService,

    private vendorProductService: VendorProductService,

    private vendorAttributeService: VendorAttributeService,

    private vendorAttributeValueService: VendorAttributeValueService

  ) {}

  // ===============================
  // Init
  // ===============================

  ngOnInit(): void {

    this.createForm();

    this.loadProducts();

    this.checkEditMode();

  }

  // ===============================
  // Form
  // ===============================

  createForm(): void {

    this.form = this.fb.group({

      sku: [
        '',
        Validators.required
      ],

      productId: [
        '',
        Validators.required
      ],

      price: [
        '',
        Validators.required
      ],

      discountPrice: [''],

      stock: [
        '',
        Validators.required
      ]

    });

  }

  // ===============================
  // Check Edit Mode
  // ===============================

  checkEditMode(): void {

    const id =
      this.route.snapshot.paramMap.get('id');

    if (!id) {

      return;

    }

    this.isEditMode = true;

    this.variantId = Number(id);

    this.loadVariant(this.variantId);

  }

  // ===============================
  // Load Products
  // ===============================

  loadProducts(): void {

    this.vendorProductService
      .getMyProducts()
      .subscribe({

        next: response => {

          this.products = response.content;

        },

        error: err => {

          console.error(err);

        }

      });

  }


  // ===============================
// Load Variant (Edit)
// ===============================

loadVariant(id: number): void {

  this.loading = true;

  this.vendorProductVariantService
    .getById(id)
    .subscribe({

      next: (variant: ProductVariantResponse) => {

        this.form.patchValue({

          sku: variant.sku,

          productId: variant.productId,

          price: variant.price,

          discountPrice: variant.discountPrice,

          stock: variant.stock

        });

        // Existing Images
        this.existingImageUrls = [...variant.imageUrls];

        this.imagePreviews = variant.imageUrls.map(
          image => this.baseImageUrl + image
        );

        // Load Attributes
        this.onProductChange();

        // Selected Attribute Values
        setTimeout(() => {

          this.dynamicAttributes.forEach(attribute => {

            const selected =
              variant.attributeValues.find(

                item =>
                  item.attributeId === attribute.attributeId

              );

            if (selected) {

              attribute.selectedValueId = selected.id;

            }

          });

        }, 300);

        this.loading = false;

      },

      error: err => {

        console.error(err);

        this.loading = false;

      }

    });

}

// ===============================
// Product Change
// ===============================

onProductChange(): void {

  const productId =
    Number(this.form.value.productId);

  const product =
    this.products.find(
      p => p.id === productId
    );

  if (!product) {

    this.dynamicAttributes = [];

    return;

  }

  this.selectedCategoryId =
    product.categoryId;

  this.loadAttributesByCategory();

}

// ===============================
// Load Attributes
// ===============================

loadAttributesByCategory(): void {

  this.vendorAttributeService
    .getAttributeByCategory(
      this.selectedCategoryId
    )
    .subscribe({

      next: response => {

        this.dynamicAttributes =
          response.content.map(item => ({

            attributeId: item.id,

            attributeName: item.name,

            values: [],

            selectedValueId: undefined

          }));

        this.dynamicAttributes.forEach(attribute => {

          this.loadAttributeValues(attribute);

        });

      },

      error: err => {

        console.error(err);

      }

    });

}

// ===============================
// Load Attribute Values
// ===============================

loadAttributeValues(
  attribute: DynamicAttribute
): void {

  this.vendorAttributeValueService
    .getAttributeValuesByAttributeId(attribute.attributeId)
    .subscribe({

      next: response => {

        attribute.values =
          response.content;

      },

      error: err => {

        console.error(err);

      }

    });

}


// ===============================
// Image Selected
// ===============================

onImageSelected(event: Event): void {

  const input = event.target as HTMLInputElement;

  if (!input.files) {

    return;

  }

  const files = Array.from(input.files);

  if (
    this.existingImageUrls.length +
    this.selectedImages.length +
    files.length > 5
  ) {

    alert('Maximum 5 images allowed.');

    input.value = '';

    return;

  }

  files.forEach(file => {

    this.selectedImages.push(file);

    const reader = new FileReader();

    reader.onload = () => {

      this.imagePreviews.push(
        reader.result as string
      );

    };

    reader.readAsDataURL(file);

  });

  input.value = '';

}

// ===============================
// Remove Image
// ===============================

removeImage(index: number): void {

  // Existing Image

  if (index < this.existingImageUrls.length) {

    this.existingImageUrls.splice(index, 1);

  }

  // Newly Added Image

  else {

    const newIndex =
      index - this.existingImageUrls.length;

    this.selectedImages.splice(
      newIndex,
      1
    );

  }

  this.imagePreviews.splice(index, 1);

}

// ===============================
// Save / Update
// ===============================

save(): void {

  if (this.form.invalid) {

    this.form.markAllAsTouched();

    return;

  }

  const dto: ProductVariantRequest = {

    sku: this.form.value.sku,

    productId: Number(
      this.form.value.productId
    ),

    price: Number(
      this.form.value.price
    ),

    discountPrice:
      this.form.value.discountPrice
        ? Number(
            this.form.value.discountPrice
          )
        : undefined,

    stock: Number(
      this.form.value.stock
    ),

    imageUrls: this.existingImageUrls,

    attributeValueIds:
      this.dynamicAttributes
        .filter(
          item =>
            item.selectedValueId != null
        )
        .map(
          item =>
            item.selectedValueId!
        )

  };

  this.loading = true;

  const request = this.isEditMode

    ? this.vendorProductVariantService.update(

        this.variantId,

        dto,

        this.selectedImages

      )

    : this.vendorProductVariantService.create(

        dto,

        this.selectedImages

      );

  request.subscribe({

    next: () => {

      this.loading = false;

      alert(

        this.isEditMode

          ? 'Variant Updated Successfully.'

          : 'Variant Created Successfully.'

      );

      this.router.navigate([
        '/vendor/vendorProductVariantList'
      ]);

    },

    error: err => {

      this.loading = false;

      console.error(err);

      alert(

        err.error?.message ||

        'Something went wrong.'

      );

    }

  });

}

// ===============================
// Reset
// ===============================

resetForm(): void {

  this.form.reset();

  this.dynamicAttributes = [];

  this.selectedImages = [];

  this.existingImageUrls = [];

  this.imagePreviews = [];

  this.selectedCategoryId = 0;

}

// ===============================
// Getter
// ===============================

get f() {

  return this.form.controls;

}


// ===============================
// Track By Image
// ===============================

trackByImage(
  index: number,
  image: string
): string {

  return image;

}

// ===============================
// Track By Attribute
// ===============================

trackByAttribute(
  index: number,
  attribute: DynamicAttribute
): number {

  return attribute.attributeId;

}

// ===============================
// Cancel
// ===============================

cancel(): void {

  this.router.navigate([
    '/admin/productVariantList'
  ]);

}

}