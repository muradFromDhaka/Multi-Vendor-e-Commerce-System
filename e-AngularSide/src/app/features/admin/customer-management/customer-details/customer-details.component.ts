import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AdminCustomerService } from '../../services/admin-customer.service';
import { UserResponse } from 'src/app/models/customer.model';

@Component({
  selector: 'app-customer-details',
  templateUrl: './customer-details.component.html',
  styleUrls: ['./customer-details.component.scss']
})
export class CustomerDetailsComponent implements OnInit {

  customer!: UserResponse;

  loading = true;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private customerService: AdminCustomerService
  ) { }

  ngOnInit(): void {

    console.log("CustomerDetailsComponent Loaded");

  const username = this.route.snapshot.paramMap.get('username');

  console.log("Username =", username);

  if (username) {
    this.loadCustomer(username);
  }

  }

  // loadCustomer(username: string): void {

  //   this.loading = true;

  //   this.customerService.getCustomer(username).subscribe({

  //     next: (res) => {

  //       this.customer = res;
  //       this.loading = false;

  //     },

  //     error: () => {

  //       this.loading = false;
  //       alert("Customer not found");

  //     }

  //   });

  // }


  loadCustomer(username: string): void {

  console.log("Username =", username);

  this.loading = true;

  this.customerService.getCustomer(username).subscribe({

    next: (res) => {

      console.log(res);

      this.customer = res;
      this.loading = false;

    },

    error: (err) => {

      console.log(err);

      this.loading = false;

      alert("Customer not found");

    }

  });

}

  enable(): void {

    this.customerService.enableCustomer(this.customer.userName)
      .subscribe(() => {

        this.customer.enabled = true;

      });

  }

  disable(): void {

    this.customerService.disableCustomer(this.customer.userName)
      .subscribe(() => {

        this.customer.enabled = false;

      });

  }

  delete(): void {

    if (!confirm("Delete this customer?")) {
      return;
    }

    this.customerService.deleteCustomer(this.customer.userName)
      .subscribe(() => {

        this.router.navigate(['/admin/customers']);

      });

  }

  back(): void {
    this.router.navigate(['/admin/customers']);
  }

}