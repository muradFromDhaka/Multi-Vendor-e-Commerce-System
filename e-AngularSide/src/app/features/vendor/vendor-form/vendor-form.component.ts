import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { VendorRequest } from 'src/app/models/vendor.model';
import { VendorService } from '../services/vendor.service';

@Component({
  selector: 'app-vendor-form',
  templateUrl: './vendor-form.component.html',
  styleUrls: ['./vendor-form.component.scss'] // ✅ এটা না থাকলে SCSS কাজ করবে না
})

export class VendorFormComponent implements OnInit {
  vendorForm!: FormGroup;
  vendorId?: number;
  isEditMode = false;

  constructor(
    private fb: FormBuilder,
    private vendorService: VendorService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.vendorForm = this.fb.group({
      shopName: ['', Validators.required],
      phone: ['', Validators.required],
      address: ['', Validators.required],
      businessEmail: ['', [Validators.required, Validators.email]],
      description: [''],
      logoUrl: [''],
      bannerUrl: [''],
    });

    this.vendorId = this.route.snapshot.params['id'];
    if (this.vendorId) {
      this.isEditMode = true;
      this.loadVendor(this.vendorId);
    }
  }

  loadVendor(id: number): void {
    this.vendorService.getVendorById(id).subscribe({
      next: (vendor) => this.vendorForm.patchValue(vendor),
      error: (err) => console.error(err),
    });
  }

  submit(): void {
    const dto: VendorRequest = this.vendorForm.value;

    if (this.isEditMode && this.vendorId) {
      this.vendorService.updateVendor(this.vendorId, dto).subscribe({
        next: () => this.router.navigate(['/vendor/vendorProfile']),
        error: (err) => console.error(err),
      });
    } else {
      this.vendorService.createVendor(dto).subscribe({
        next: () => this.router.navigate(['/vendor/vendorProfile']),
        error: (err) => console.error(err),
      });
    }
  }
}
