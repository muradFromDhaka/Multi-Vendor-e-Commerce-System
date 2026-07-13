import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { VendorResponse } from 'src/app/models/vendor.model';
import { VendorService } from '../services/vendor.service';

@Component({
  selector: 'app-vendor-details',
  templateUrl: './vendor-details.component.html',
})
export class VendorDetailsComponent implements OnInit {
  vendor?: VendorResponse;

  constructor(
    private route: ActivatedRoute,
    private vendorService: VendorService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];
    this.vendorService.getVendorById(id).subscribe({
      next: (data) => (this.vendor = data),
      error: (err) => console.error(err),
    });
  }
}
