import { Component, OnInit } from '@angular/core';

import { PageResponse } from 'src/app/models/PageResponse';
import { VendorCustomerResponse } from '../../models/vendor-customer.model';
import { VendorCustomerService } from '../../services/vendor-customer.service';

@Component({
  selector: 'app-vendor-customer-list',
  templateUrl: './vendor-customer-list.component.html',
  styleUrls: ['./vendor-customer-list.component.scss']
})
export class VendorCustomerListComponent implements OnInit {

  customers: VendorCustomerResponse[] = [];

  loading = false;

  errorMessage = '';

  // Pagination
  page = 0;
  size = 10;
  totalElements = 0;
  totalPages = 0;

  // Sorting
  sort = 'createdAt';
  direction = 'desc';

  // Search (Future)
  keyword = '';

  constructor(
    private vendorCustomerService: VendorCustomerService
  ) { }

  ngOnInit(): void {

    this.loadCustomers();

  }

  loadCustomers(): void {

    this.loading = true;
    this.errorMessage = '';

    this.vendorCustomerService
      .getAllCustomers(
        this.page,
        this.size
      )
      .subscribe({

        next: (response: PageResponse<VendorCustomerResponse>) => {

          this.customers = response.content;

          this.page = response.number;
          this.size = response.size;

          this.totalElements = response.totalElements;
          this.totalPages = response.totalPages;

          this.loading = false;

        },

        error: (error) => {

          console.error(error);

          this.loading = false;

          this.errorMessage = 'Failed to load customers.';

        }

      });

  }

  onPageChange(event: any): void {

    this.page = event.pageIndex;
    this.size = event.pageSize;

    this.loadCustomers();

  }

  refresh(): void {

    this.loadCustomers();

  }

  clearSearch(): void {

    this.keyword = '';

    this.page = 0;

    this.loadCustomers();

  }

}