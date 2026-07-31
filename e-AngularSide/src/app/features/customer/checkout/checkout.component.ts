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

  loading = false;

  constructor(
    private router: Router,
    private checkoutService: CheckoutService
  ) {}

  ngOnInit(): void {

  }

  selectedPayment!: CheckoutPaymentSelection;

onPaymentSelected(payment: CheckoutPaymentSelection): void {

  this.selectedPayment = payment;

  this.orderRequest.paymentMethod = payment.paymentMethod;

}

  onAddressSubmitted(address: OrderRequest): void {

    this.orderRequest.shippingAddressId = address.shippingAddressId;

    this.orderRequest.shippingAddress = address.shippingAddress;

  }


  placeOrder(): void {

    console.log(this.orderRequest);

    if (this.loading) {
    return;
  }

  this.loading = true;

    this.checkoutService.placeOrder(this.orderRequest)
      .subscribe({

        next: (response) => {

          // console.log(this.orderRequest);

          this.loading = false;

          alert('Order placed successfully');

          this.router.navigate(['/customer/orders', response.id]);

        },

        error: (err) => {

          this.loading = false;

          if (err.status === 409) {

          alert(err.error.message);
 
         return;
       }

           alert('Failed to place order');
    }

      });

  }

}