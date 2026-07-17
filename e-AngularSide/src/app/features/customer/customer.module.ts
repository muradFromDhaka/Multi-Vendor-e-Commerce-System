import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomerRoutingModule } from './customer-routing.module';
import { CustomerComponent } from './customer.component';
import { NavbarComponent } from './navbar/navbar.component';
import { CartComponent } from './cart/cart.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { CheckoutAddressComponent } from './checkout/checkout-address/checkout-address.component';
import { CheckoutPaymentComponent } from './checkout/checkout-payment/checkout-payment.component';
import { OrderListComponent } from './order-list/order-list.component';
import { OrderDetailsComponent } from './order-details/order-details.component';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CheckoutSummaryComponent } from './checkout/checkout-summary/checkout-summary.component';


@NgModule({
  declarations: [
    CustomerComponent,
    NavbarComponent,
    CartComponent,
    CheckoutComponent,
    CheckoutAddressComponent,
    CheckoutPaymentComponent,
    CheckoutSummaryComponent,
    OrderListComponent,
    OrderDetailsComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    CustomerRoutingModule,
    ReactiveFormsModule
  ]
})
export class CustomerModule { }
