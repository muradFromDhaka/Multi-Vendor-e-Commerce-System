import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { PaymentMethod } from 'src/app/models/order.model';

@Component({
  selector: 'app-checkout-payment',
  templateUrl: './checkout-payment.component.html',
  styleUrls: ['./checkout-payment.component.scss']
})
export class CheckoutPaymentComponent implements OnInit {

  @Output()
  paymentSelected = new EventEmitter<PaymentMethod>();

  paymentMethods = Object.values(PaymentMethod);

  selectedPaymentMethod!: PaymentMethod;

  ngOnInit(): void {
    this.selectedPaymentMethod = PaymentMethod.CASH_ON_DELIVERY;
    this.paymentSelected.emit(this.selectedPaymentMethod);
  }

  selectPayment(method: PaymentMethod): void {
    this.selectedPaymentMethod = method;
    this.paymentSelected.emit(method);
  }

}