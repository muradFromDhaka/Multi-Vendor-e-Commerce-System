import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import {
  VendorRequest,
  VendorResponse
} from 'src/app/models/vendor.model';

import { AdminVendorService } from '../../services/admin-vendor.service';
import { environment } from 'src/app/services/environments';

@Component({
  selector: 'app-vendor-edit',
  templateUrl: './vendor-edit.component.html',
  styleUrls: ['./vendor-edit.component.scss']
})
export class VendorEditComponent implements OnInit {

  environment = environment;
  vendorId!: number;

  vendor?: VendorResponse;

  loading = false;
  saving = false;

  form!: FormGroup;

logoFile?: File;
bannerFile?: File;

logoPreview?: string;
bannerPreview?: string;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private adminVendorService: AdminVendorService
  ) {}

  ngOnInit(): void {

    this.vendorId = Number(
      this.route.snapshot.paramMap.get('id')
    );

    this.createForm();

    this.loadVendor();

  }

  private createForm() {

    this.form = this.fb.group({

      shopName: ['', Validators.required],

      description: [''],

      businessEmail: ['', Validators.email],

      phone: [''],

      address: ['']

    });

  }

  loadVendor() {

    this.loading = true;

    this.adminVendorService
      .getVendorById(this.vendorId)
      .subscribe({

        next: vendor => {

          this.vendor = vendor;

          this.form.patchValue({

            shopName: vendor.shopName,
            description: vendor.description,
            businessEmail: vendor.businessEmail,
            phone: vendor.phone,
            address: vendor.address,
          });

           this.logoPreview = vendor.logoUrl
              ? this.environment.baseImageUrl + vendor.logoUrl
              : '';

           this.bannerPreview = vendor.bannerUrl
              ? this.environment.baseImageUrl + vendor.bannerUrl
              : '';

           this.loading = false;

        },

        error: err => {

          console.error(err);

          this.loading = false;

        }

      });

  }


  onLogoChange(event: any) {

  const file = event.target.files?.[0];

  if (!file) {
    return;
  }

  this.logoFile = file;

  const reader = new FileReader();

  reader.onload = () => {
    this.logoPreview = reader.result as string;
  };

  reader.readAsDataURL(file);

}

onBannerChange(event: any) {

  const file = event.target.files?.[0];

  if (!file) {
    return;
  }

  this.bannerFile = file;

  const reader = new FileReader();

  reader.onload = () => {
    this.bannerPreview = reader.result as string;
  };

  reader.readAsDataURL(file);

}

  save() {

    if (this.form.invalid) {

      this.form.markAllAsTouched();

      return;

    }

    this.saving = true;

    const request: VendorRequest = this.form.value;

    this.adminVendorService.updateVendor(
    this.vendorId,
    request,
    this.logoFile,
    this.bannerFile
     )
      .subscribe({

        next: () => {

          this.saving = false;

          this.router.navigate([
            '/admin/vendors',
            this.vendorId
          ]);

        },

        error: err => {

          console.error(err);

          this.saving = false;

        }

      });

  }

  cancel() {

    this.router.navigate([
      '/admin/vendors',
      this.vendorId
    ]);

  }

}