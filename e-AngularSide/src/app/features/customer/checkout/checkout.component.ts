import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { OrderRequest, PaymentMethod } from 'src/app/models/order.model';

import { CheckoutService } from '../services/checkout.service';
import { CheckoutPaymentSelection } from './checkout-payment/checkout-payment.component';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss']
})
export class CheckoutComponent implements OnInit {

  orderRequest: OrderRequest = {
    paymentMethod: PaymentMethod.CASH_ON_DELIVERY
  };

  constructor(
    private router: Router,
    private checkoutService: CheckoutService
  ) {}

  ngOnInit(): void {

  }

  selectedPayment!: CheckoutPaymentSelection;

onPaymentSelected(
  payment: CheckoutPaymentSelection
) {

  this.selectedPayment = payment;

}

  onAddressSubmitted(address: OrderRequest): void {

    this.orderRequest.shippingAddressId = address.shippingAddressId;

    this.orderRequest.shippingAddress = address.shippingAddress;

  }

  // onPaymentSelected(paymentMethod: PaymentMethod): void {

  //   this.orderRequest.paymentMethod = paymentMethod;

  // }

  placeOrder(): void {

    this.checkoutService.placeOrder(this.orderRequest)
      .subscribe({

        next: (response) => {

          console.log(this.orderRequest);

          alert('Order placed successfully');

          this.router.navigate(['/customer/orders', response.id]);

        },

        error: (err) => {

          console.error(err);

          alert('Failed to place order');

        }

      });

  }

}