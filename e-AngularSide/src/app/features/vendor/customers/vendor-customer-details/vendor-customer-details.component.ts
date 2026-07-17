import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { VendorCustomerService } from '../../services/vendor-customer.service';
import { VendorCustomerDetailsResponse } from '../../models/vendor-customer.model';

@Component({
  selector: 'app-vendor-customer-details',
  templateUrl: './vendor-customer-details.component.html',
  styleUrls: ['./vendor-customer-details.component.scss']
})
export class VendorCustomerDetailsComponent implements OnInit {

  customer?: VendorCustomerDetailsResponse;

  loading = false;

  errorMessage = '';

  userName = '';

  constructor(
    private route: ActivatedRoute,
    private vendorCustomerService: VendorCustomerService
  ) { }

  ngOnInit(): void {

    this.route.paramMap.subscribe(params => {

      const userName = params.get('userName');

      if (userName) {

        this.userName = userName;

        this.loadCustomerDetails();

      }

    });

  }

  loadCustomerDetails(): void {

    this.loading = true;

    this.errorMessage = '';

    this.vendorCustomerService
      .getCustomerDetails(this.userName)
      .subscribe({

        next: (response) => {

          this.customer = response;

          this.loading = false;

        },

        error: (error) => {

          console.error(error);

          this.loading = false;

          this.errorMessage = 'Failed to load customer details.';

        }

      });

  }

  refresh(): void {

    this.loadCustomerDetails();

  }

}