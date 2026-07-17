import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { OrderRequest } from 'src/app/models/order.model';


@Component({
  selector: 'app-checkout-address',
  templateUrl: './checkout-address.component.html',
  styleUrls: ['./checkout-address.component.scss']
})
export class CheckoutAddressComponent implements OnInit {

  @Output()
  addressSubmitted = new EventEmitter<OrderRequest>();

  addressForm!: FormGroup;

  useExistingAddress = false;

  selectedAddressId?: number;

  constructor(
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {

    this.addressForm = this.fb.group({

      customerName: ['', Validators.required],

      phone: ['', Validators.required],

      street: ['', Validators.required],

      city: ['', Validators.required],

      district: ['', Validators.required],

      country: ['Bangladesh', Validators.required],

      zipCode: ['', Validators.required]

    });

  }

  submit(): void {

    const request: OrderRequest = {

      shippingAddressId: this.useExistingAddress
        ? this.selectedAddressId!
        : undefined,

      shippingAddress: this.useExistingAddress
        ? undefined
        : this.addressForm.value,

      paymentMethod: undefined!

    };

    console.log("request:---------",request)
    
    this.addressSubmitted.emit(request);

  }

}