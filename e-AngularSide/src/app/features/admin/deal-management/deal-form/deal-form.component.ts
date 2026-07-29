import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { DealService } from 'src/app/services/deal.service';
import { ProductService } from 'src/app/services/product.service';

import { DealResponse } from 'src/app/models/deal.model';
import { ProductListResponse } from 'src/app/models/product.model';

@Component({
  selector: 'app-deal-form',
  templateUrl: './deal-form.component.html',
  styleUrls: ['./deal-form.component.scss']
})
export class DealFormComponent implements OnInit {

  dealForm!: FormGroup;

  products: ProductListResponse[] = [];

  dealId!: number;

  isEditMode = false;

  loading = false;

  constructor(
    private fb: FormBuilder,
    private dealService: DealService,
    private productService: ProductService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {

    this.initForm();

    this.loadProducts();

    this.dealId = Number(this.route.snapshot.paramMap.get('id'));

    if (this.dealId) {

      this.isEditMode = true;

      this.loadDeal();

    }

  }

  initForm() {

    this.dealForm = this.fb.group({

      title: ['', Validators.required],

      discountPercent: [
        10,
        [
          Validators.required,
          Validators.min(1),
          Validators.max(100)
        ]
      ],

      productId: [
        null,
        Validators.required
      ],

      active: [true],

      startTime: ['', Validators.required],

      endTime: ['', Validators.required]

    });

  }

  loadProducts() {

    this.productService
      .getAllProducts(0, 500)
      .subscribe({

        next: res => {

          this.products = res.content;

        },

        error: err => console.error(err)

      });

  }

  loadDeal() {

    this.dealService
      .getDealById(this.dealId)
      .subscribe({

        next: deal => {

          this.dealForm.patchValue({

            title: deal.title,

            discountPercent: deal.discountPercent,

            productId: deal.productId,

            active: deal.active,

            startTime: this.toDateTimeLocal(deal.startTime),

            endTime: this.toDateTimeLocal(deal.endTime)

          });

        },

        error: err => console.error(err)

      });

  }

  submit() {

     console.log(this.dealForm.valid);
  console.log(this.dealForm.value);
  console.log(this.dealForm.errors);
  
    if (this.dealForm.invalid) {

      this.dealForm.markAllAsTouched();

      return;

    }

    this.loading = true;

    if (this.isEditMode) {

      this.dealService
        .updateDeal(this.dealId, this.dealForm.value)
        .subscribe({

          next: () => {

            this.loading = false;

            this.router.navigate(['/admin/deal-management/deal']);

          },

          error: err => {

            console.error(err);

            this.loading = false;

          }

        });

    } else {

      this.dealService
        .createDeal(this.dealForm.value)
        .subscribe({

          next: () => {

            console.log("Deal created successfully");

            this.loading = false;

            this.router.navigate(['/admin/deal-management/deal']);

          },

          error: err => {

            console.error(err);

            this.loading = false;

          }

        });

    }

  }

  cancel() {

    this.router.navigate(['/admin/deal-management/deal']);

  }

  private toDateTimeLocal(date: string): string {

    return date?.substring(0, 16);

  }

}