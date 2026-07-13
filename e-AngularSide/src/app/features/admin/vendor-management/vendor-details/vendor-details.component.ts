import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/app/services/environments';

import { VendorResponse, VendorStats } from 'src/app/models/vendor.model';

import { AdminVendorService } from '../../services/admin-vendor.service';

@Component({
  selector: 'app-vendor-details',
  templateUrl: './vendor-details.component.html',
  styleUrls: ['./vendor-details.component.scss']
})
export class VendorDetailsComponent implements OnInit {

  vendor?: VendorResponse;
  stats: VendorStats = {
  totalProducts: 0,
  totalOrders: 0,
  totalRevenue: 0.00,
  totalCustomers: 0
};

  vendorId!: number;

  loading = false;
  updating = false;

  environment = environment;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    // private vendorService: VendorService,
    private adminVendorService: AdminVendorService
  ) {}

  ngOnInit(): void {

  this.vendorId = Number(
    this.route.snapshot.paramMap.get('id')
  );

  if (!this.vendorId) {
    return;
  }

  this.loadVendor();
  this.loadStats();

}

 loadStats(): void {

  this.adminVendorService
      .getVendorStats(this.vendorId)
      .subscribe({

        next: stats => {
          this.stats = stats;
        },

        error: err => console.error(err)

      });
}


  loadVendor(): void {

  this.loading = true;

  this.adminVendorService
      .getVendorById(this.vendorId)
      .subscribe({
        next: vendor => {
          this.vendor = vendor;
          this.loading = false;
        },

        error: err => {
          console.error(err);
          this.loading = false;
        }
      });
}


  // ============================
  // Image
  // ============================

  getImageUrl(image?: string): string {

    if (!image) {
      return 'assets/images/no-image.png';
    }

    if (
      image.startsWith('http://') ||
      image.startsWith('https://')
    ) {
      return image;
    }

    return environment.baseImageUrl + image;
  }

  // ============================
  // Approve
  // ============================

  approveVendor(): void {

    if (!this.vendor?.id) {
      return;
    }

    if (!confirm('Approve this vendor?')) {
      return;
    }

    this.updating = true;

    this.adminVendorService
      .approveVendor(this.vendor.id)
      .subscribe({

        next: (vendor) => {

          this.vendor = vendor;
          this.updating = false;

        },

        error: (err) => {

          console.error(err);
          this.updating = false;

        }

      });

  }

  // ============================
  // Reject
  // ============================

  rejectVendor(): void {

    if (!this.vendor?.id) {
      return;
    }

    if (!confirm('Reject this vendor?')) {
      return;
    }

    this.updating = true;

    this.adminVendorService
      .rejectVendor(this.vendor.id)
      .subscribe({

        next: (vendor) => {

          this.vendor = vendor;
          this.updating = false;

        },

        error: (err) => {

          console.error(err);
          this.updating = false;

        }

      });

  }

  // ============================
  // Suspend
  // ============================

  suspendVendor(): void {

    if (!this.vendor?.id) {
      return;
    }

    if (!confirm('Suspend this vendor?')) {
      return;
    }

    this.updating = true;

    this.adminVendorService
      .suspendVendor(this.vendor.id)
      .subscribe({

        next: (vendor) => {

          this.vendor = vendor;
          this.updating = false;

        },

        error: (err) => {

          console.error(err);
          this.updating = false;

        }

      });

  }

  // ============================
  // Activate
  // ============================

  activateVendor(): void {

    if (!this.vendor?.id) {
      return;
    }

    this.updating = true;

    this.adminVendorService
      .activateVendor(this.vendor.id)
      .subscribe({

        next: (vendor) => {

          this.vendor = vendor;
          this.updating = false;

        },

        error: (err) => {

          console.error(err);
          this.updating = false;

        }

      });

  }

  // ============================
  // Edit
  // ============================

  editVendor() {

  this.router.navigate([
    '/admin/vendors/edit',
    this.vendorId
  ]);

}

viewVendorProducts() {

  this.router.navigate(['/admin/vendors',this.vendorId,'products']);

}

viewVendorOrders() {

this.router.navigate(['/admin/vendors',this.vendorId,'orders']);

}

}