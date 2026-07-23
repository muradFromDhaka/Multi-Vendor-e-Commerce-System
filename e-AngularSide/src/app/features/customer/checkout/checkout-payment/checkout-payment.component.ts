import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import {
  PaymentMethod,
  PaymentProvider
} from 'src/app/models/order.model';

export interface CheckoutPaymentSelection {

  paymentMethod: PaymentMethod;
  provider: PaymentProvider;

}

@Component({
  selector: 'app-checkout-payment',
  templateUrl: './checkout-payment.component.html',
  styleUrls: ['./checkout-payment.component.scss']
})
export class CheckoutPaymentComponent implements OnInit {

  @Output()
  paymentSelected =
    new EventEmitter<CheckoutPaymentSelection>();

  paymentOptions = [
    {
      label: 'Cash On Delivery',
      description: 'Pay when your order is delivered.',
      method: PaymentMethod.CASH_ON_DELIVERY,
      provider: PaymentProvider.MANUAL,
      icon: 'fa-money-bill-wave'
    },
   
    {
      label: 'Card',
      description: 'Visa / MasterCard / Amex',
      method: PaymentMethod.CARD,
      provider: PaymentProvider.STRIPE,
      icon: 'fa-credit-card'
    },
    {
      label: 'Bank Transfer',
      description: 'Transfer directly from your bank.',
      method: PaymentMethod.BANK_TRANSFER,
      provider: PaymentProvider.MANUAL,
      icon: 'fa-building-columns'
    },
    {
      label: 'Mobile Banking',
      description: 'Other mobile banking services.',
      method: PaymentMethod.MOBILE_BANKING,
      provider: PaymentProvider.MANUAL,
      icon: 'fa-mobile-screen-button'
    }
  ];

  selectedPaymentMethod!: PaymentMethod;

  ngOnInit(): void {

    const defaultPayment = this.paymentOptions[0];

    this.selectedPaymentMethod = defaultPayment.method;

    this.paymentSelected.emit({
      paymentMethod: defaultPayment.method,
      provider: defaultPayment.provider
    });

  }

  selectPayment(option: any): void {

    this.selectedPaymentMethod = option.method;

    this.paymentSelected.emit({
      paymentMethod: option.method,
      provider: option.provider
    });

  }

}