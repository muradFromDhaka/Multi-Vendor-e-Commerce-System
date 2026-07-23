import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderResponse, OrderStatus, PaymentStatus } from 'src/app/models/order.model';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.scss']
})
export class OrderDetailsComponent implements OnInit {

  order?: OrderResponse;
  OrderStatus = OrderStatus;

  loading = false;

  error = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private orderService: OrderService
  ) { }

  ngOnInit(): void {

    const id = Number(this.route.snapshot.paramMap.get('id'));

    if (!id) {
      this.router.navigate(['/orders']);
      return;
    }

    this.loadOrder(id);
  }

  loadOrder(id: number): void {

    this.loading = true;

    this.orderService.getOrder(id).subscribe({

      next: (response) => {

        this.order = response;
        this.loading = false;
      },

      error: (err) => {

        this.loading = false;
        this.error = err.error?.message ?? 'Unable to load order.';
      }

    });

  }

  cancelOrder(): void {

    if (!this.order) {
      return;
    }

    if (!confirm('Are you sure you want to cancel this order?')) {
      return;
    }

    this.orderService.cancelOrder(this.order.id).subscribe({

      next: (response) => {

        this.order = response;
        alert('Order cancelled successfully.');
      },

      error: (err) => {

        alert(err.error?.message ?? 'Failed to cancel order.');
      }

    });

  }

  getOrderStatusClass(status: OrderStatus): string {
    switch (status) {
    case OrderStatus.PENDING:
      return 'bg-warning';
    case OrderStatus.PROCESSING:
      return 'bg-info';
    case OrderStatus.SHIPPED:
      return 'bg-primary';
    case OrderStatus.DELIVERED:
      return 'bg-success';
    case OrderStatus.CANCELLED:
      return 'bg-danger';
    case OrderStatus.PARTIALLY_DELIVERED:
      return 'bg-dark';
    case OrderStatus.PARTIALLY_CANCELLED:
      return 'bg-secondary';
    case OrderStatus.RETURNED:
      return 'text-bg-light border';
    default:
      return 'bg-secondary';
  }
}

getPaymentStatusClass(status: PaymentStatus): string {

  switch (status) {

    case PaymentStatus.PENDING:
      return 'pending';

    case PaymentStatus.PAID:
      return 'paid';

    case PaymentStatus.FAILED:
      return 'failed';

    case PaymentStatus.CANCELLED:
      return 'cancelled';

    case PaymentStatus.REFUNDED:
      return 'refunded';

    default:
      return '';
  }

}

  goBack(): void {
    this.router.navigate(['/orders']);
  }

}