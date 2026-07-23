import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import {
  PaymentMethod,
  PaymentStatus
} from 'src/app/models/order.model';

import {
  PaymentResponse,
  UpdatePaymentStatusRequest
} from 'src/app/shared/models/payment.model';
import { AdminPaymentService } from '../../services/admin-payment.service';


@Component({
  selector: 'app-payment-details',
  templateUrl: './payment-details.component.html',
  styleUrls: ['./payment-details.component.scss']
})
export class PaymentDetailsComponent implements OnInit {

  payment!: PaymentResponse;

  loading = false;

  updating = false;

  paymentStatuses = Object.values(PaymentStatus);

  selectedStatus!: PaymentStatus;

  transactionId = '';

  refundTransactionId = '';

  refundedAmount?: number;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private paymentService: AdminPaymentService
  ) {}

  ngOnInit(): void {

    const paymentId = Number(
      this.route.snapshot.paramMap.get('id')
    );

    this.loadPayment(paymentId);

  }

  loadPayment(paymentId: number): void {

    this.loading = true;

    this.paymentService
      .getPaymentById(paymentId)
      .subscribe({

        next: (response) => {

          this.payment = response;

          this.selectedStatus = response.paymentStatus;

          this.loading = false;

        },

        error: err => {

          console.error(err);

          this.loading = false;

        }

      });

  }

  updateStatus(): void {

    const request: UpdatePaymentStatusRequest = {

      paymentStatus: this.selectedStatus,

      transactionId: this.transactionId || undefined,

      refundTransactionId:
        this.refundTransactionId || undefined,

      refundedAmount: this.refundedAmount

    };

    this.updating = true;

    this.paymentService
      .updatePaymentStatus(
        this.payment.paymentId,
        request
      )
      .subscribe({

        next: response => {

          this.payment = response;

          this.selectedStatus =
            response.paymentStatus;

          this.updating = false;

          alert('Payment updated successfully.');

        },

        error: err => {

          console.error(err);

          this.updating = false;

          alert(err.error.message);

        }

      });

  }

  back(): void {

    this.router.navigate(['/admin/payments']);

  }

}