import { Component, OnInit } from '@angular/core';
import { VendorResponse } from 'src/app/models/vendor.model';
import { VendorService } from '../../services/vendor.service';

@Component({
  selector: 'app-vendor-profile',
  templateUrl: './vendor-profile.component.html',
  styleUrls: ['./vendor-profile.component.scss']
})
export class VendorProfileComponent implements OnInit {
  vendor?: VendorResponse;

  constructor(private vendorService: VendorService) {}

  ngOnInit(): void {
    this.vendorService.getMyVendor().subscribe({
      next: (data) => (this.vendor = data),
      error: (err) => console.error(err),
    });
  }
}
