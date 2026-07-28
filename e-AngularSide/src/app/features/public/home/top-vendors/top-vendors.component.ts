import { Component, OnInit } from '@angular/core';
import { environment } from 'src/app/services/environments';
import { TopVendorResponse } from 'src/app/models/vendor.model';
import { VendorService } from 'src/app/features/vendor/services/vendor.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-top-vendors',
  templateUrl: './top-vendors.component.html',
  styleUrls: ['./top-vendors.component.scss']
})
export class TopVendorsComponent implements OnInit {

  vendors: TopVendorResponse[] = [];

  loading = true;

  baseImageUrl = environment.baseImageUrl;

  constructor(
    private vendorService: VendorService,
    private router: Router
  ) {}

  ngOnInit(): void {

    this.loadTopVendors();

  }

  loadTopVendors(): void {

    this.loading = true;

    this.vendorService
      .getTopVendors()
      .subscribe({

        next: (res) => {

          // console.log("Top Vendors Response:", res);

          this.vendors = res.content;

          // console.log(" Vendors:---------------------", this.vendors);

          this.loading = false;

        },

        error: (err) => {

          console.error(err);

          this.loading = false;

        }

      });

  }

  getLogoUrl(vendor: TopVendorResponse): string {

    if (!vendor.logoUrl) {

      return 'assets/images/default-shop.png';

    }

    return vendor.logoUrl.startsWith('http')

      ? vendor.logoUrl

      : this.baseImageUrl + vendor.logoUrl;

  }


  openVendorShop(vendorId: number): void {

  this.router.navigate([
    '/vendorShop',
    vendorId
  ]);

}

}