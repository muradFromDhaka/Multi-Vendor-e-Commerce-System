import { Component, OnInit } from '@angular/core';
import { AdminCustomerService } from '../../services/admin-customer.service';
import { CustomerStatistics, UserResponse } from 'src/app/models/customer.model';
import { PageResponse } from 'src/app/models/PageResponse';

@Component({
  selector: 'app-customer-list',
  templateUrl: './customer-list.component.html',
  styleUrls: ['./customer-list.component.scss']
})
export class CustomerListComponent implements OnInit {

customers: UserResponse[] = [];


statistics!: CustomerStatistics;

selectedFilter: 'ALL' | 'ACTIVE' | 'DISABLED' = 'ALL';

searchTerm = '';

loading = false;

page = 0;
size = 10;

totalPages = 0;
totalElements = 0;


  constructor(
    private customerService: AdminCustomerService
  ) {}

  ngOnInit(): void {

    this.loadStatistics();
    this.loadCustomers();

  }

  loadCustomers(): void {

    this.loading = true;

    this.customerService
      .getAllCustomers(this.page, this.size)
      .subscribe({

        next: (response: PageResponse<UserResponse>) => {

          this.customers = response.content;
          this.totalPages = response.totalPages;
          this.totalElements = response.totalElements;

          this.loading = false;
        },

        error: () => {

          this.loading = false;
        }

      });

  }


  loadStatistics(): void {

  this.customerService
      .getCustomerStatistics()
      .subscribe(res => {

        this.statistics = res;

      });

}


changeFilter(filter: 'ALL' | 'ACTIVE' | 'DISABLED') {

    this.selectedFilter = filter;

    this.page = 0;

    switch (filter) {

      case 'ALL':
        this.loadCustomers();
        break;

      case 'ACTIVE':
        this.loadActiveCustomers();
        break;

      case 'DISABLED':
        this.loadDisabledCustomers();
        break;

    }

}


loadActiveCustomers() {

    this.loading = true;

    this.customerService
        .getActiveCustomers(this.page, this.size)
        .subscribe(res => {

            this.customers = res.content;

            this.totalPages = res.totalPages;
            this.totalElements = res.totalElements;

            this.loading = false;

        });

}



loadDisabledCustomers() {

    this.loading = true;

    this.customerService
        .getDisabledCustomers(this.page, this.size)
        .subscribe(res => {

            this.customers = res.content;

            this.totalPages = res.totalPages;
            this.totalElements = res.totalElements;

            this.loading = false;

        });

}


  searchCustomer(): void {

    if (!this.searchTerm.trim()) {
    this.loadByFilter();
    return;
}

    this.loading = true;

    this.customerService
      .searchCustomers(
        this.searchTerm,
        this.page,
        this.size
      )
      .subscribe({

        next: (response) => {

          this.customers = response.content;
          this.totalPages = response.totalPages;
          this.totalElements = response.totalElements;

          this.loading = false;
        },

        error: () => {

          this.loading = false;
        }

      });

  }

 enable(customer: UserResponse) {

  this.customerService.enableCustomer(customer.userName)
    .subscribe(() => {

      this.loadStatistics();
      this.loadByFilter();

    });

}

disable(customer: UserResponse) {

  this.customerService.disableCustomer(customer.userName)
    .subscribe(() => {

      this.loadStatistics();
      this.loadByFilter();

    });

}

delete(customer: UserResponse) {

  if (!confirm('Delete this customer?')) {
    return;
  }

  this.customerService.deleteCustomer(customer.userName)
    .subscribe(() => {

      this.loadStatistics();
      this.loadByFilter();

    });

}


private loadByFilter() {

    switch (this.selectedFilter) {

        case 'ALL':
            this.loadCustomers();
            break;

        case 'ACTIVE':
            this.loadActiveCustomers();
            break;

        case 'DISABLED':
            this.loadDisabledCustomers();
            break;

    }

}


  previousPage() {

    if (this.page > 0) {

        this.page--;

        this.loadByFilter();

    }

}

  nextPage() {

    if (this.page < this.totalPages - 1) {

        this.page++;

        this.loadByFilter();

    }

}

}