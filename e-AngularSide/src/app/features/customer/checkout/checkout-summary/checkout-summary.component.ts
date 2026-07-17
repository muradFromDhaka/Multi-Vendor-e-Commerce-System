import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { CheckoutService } from '../../services/checkout.service';
import { CartDto } from 'src/app/models/cart.model';

@Component({
  selector: 'app-checkout-summary',
  templateUrl: './checkout-summary.component.html',
  styleUrls: ['./checkout-summary.component.scss']
})
export class CheckoutSummaryComponent implements OnInit {

  @Output()
  placeOrder = new EventEmitter<void>();

  cart?: CartDto;

  loading = false;

  constructor(
    private checkoutService: CheckoutService
  ) {}

  ngOnInit(): void {
    this.loadSummary();
  }

  loadSummary(): void {

    this.loading = true;

    this.checkoutService.getCheckoutSummary().subscribe({

      next: (response) => {
        this.cart = response;
        this.loading = false;
      },

      error: () => {
        this.loading = false;
      }

    });

  }

  onPlaceOrder(): void {
    this.placeOrder.emit();
  }

}