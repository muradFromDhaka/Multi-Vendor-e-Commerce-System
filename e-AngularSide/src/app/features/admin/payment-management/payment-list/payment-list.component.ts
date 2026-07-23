import { Component, OnInit } from '@angular/core';

import {
  PaymentMethod,
  PaymentStatus,
} from 'src/app/models/order.model';

import { AdminPaymentService } from '../../services/admin-payment.service';
import { PaymentResponse } from 'src/app/shared/models/payment.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-payment-list',
  
  templateUrl: './payment-list.component.html',
  styleUrls: ['./payment-list.component.scss']
})
export class PaymentListComponent implements OnInit {

  constructor(
    private router: Router,
    private paymentService: AdminPaymentService
  ) {}

  // ===========================
  // Payment Data
  // ===========================

  payments: PaymentResponse[] = [];

  selectedPayment?: PaymentResponse;

  loading = false;

  // ===========================
  // Pagination
  // ===========================

  page = 0;

  size = 10;

  totalElements = 0;

  totalPages = 0;

  // ===========================
  // Filter
  // ===========================

  selectedStatus?: PaymentStatus;

  selectedMethod?: PaymentMethod;

  searchKeyword = '';

  // ===========================
  // Enum
  // ===========================

  paymentStatuses = Object.values(PaymentStatus);

  paymentMethods = Object.values(PaymentMethod);

  ngOnInit(): void {

    this.loadPayments();

  }

  // ===========================
  // Load All Payments
  // ===========================

  loadPayments(): void {

    this.loading = true;

    this.paymentService
      .getAllPayments(this.page, this.size)
      .subscribe({

        next: (response) => {

          this.payments = response.content;

          this.totalElements = response.totalElements;

          this.totalPages = response.totalPages;

          this.loading = false;

        },

        error: (err) => {

          console.error(err);

          this.loading = false;

        }

      });

  }

  // ===========================
  // Pagination
  // ===========================

  onPageChange(event: any): void {

    this.page = event.pageIndex;

    this.size = event.pageSize;

    this.loadPayments();

  }

  // ===========================
  // Status Filter
  // ===========================

  filterByStatus(): void {

    if (!this.selectedStatus) {

      this.loadPayments();

      return;

    }

    this.loading = true;

    this.paymentService
      .getPaymentsByStatus(
        this.selectedStatus,
        this.page,
        this.size
      )
      .subscribe({

        next: (response) => {

          this.payments = response.content;

          this.totalElements = response.totalElements;

          this.totalPages = response.totalPages;

          this.loading = false;

        },

        error: () => this.loading = false

      });

  }

  // ===========================
  // Payment Method Filter
  // ===========================

  filterByMethod(): void {

    if (!this.selectedMethod) {

      this.loadPayments();

      return;

    }

    this.loading = true;

    this.paymentService
      .getPaymentsByMethod(
        this.selectedMethod,
        this.page,
        this.size
      )
      .subscribe({

        next: (response) => {

          this.payments = response.content;

          this.totalElements = response.totalElements;

          this.totalPages = response.totalPages;

          this.loading = false;

        },

        error: () => this.loading = false

      });

  }

  // ===========================
  // Combined Filter
  // ===========================

  applyFilter(): void {

    if (!this.selectedStatus || !this.selectedMethod) {

      this.loadPayments();

      return;

    }

    this.loading = true;

    this.paymentService
      .filterPayments(
        this.selectedStatus,
        this.selectedMethod,
        this.page,
        this.size
      )
      .subscribe({

        next: (response) => {

          this.payments = response.content;

          this.totalElements = response.totalElements;

          this.totalPages = response.totalPages;

          this.loading = false;

        },

        error: () => this.loading = false

      });

  }

  // ===========================
  // Search Customer
  // ===========================

  searchCustomer(): void {

    if (!this.searchKeyword.trim()) {

      this.loadPayments();

      return;

    }

    this.loading = true;

    this.paymentService
      .searchCustomerPayments(
        this.searchKeyword,
        this.page,
        this.size
      )
      .subscribe({

        next: (response) => {

          this.payments = response.content;

          this.totalElements = response.totalElements;

          this.totalPages = response.totalPages;

          this.loading = false;

        },

        error: () => this.loading = false

      });

  }

  // ===========================
  // View Payment
  // ===========================

  // viewPayment(payment: PaymentResponse): void {

  //   this.selectedPayment = payment;

  // }

  viewPayment(payment: PaymentResponse) {
  this.router.navigate([
    '/admin/payments',
    payment.paymentId
  ]);
}

  // ===========================
  // Refresh
  // ===========================

  refresh(): void {

    this.selectedStatus = undefined;

    this.selectedMethod = undefined;

    this.searchKeyword = '';

    this.page = 0;

    this.loadPayments();

  }

}