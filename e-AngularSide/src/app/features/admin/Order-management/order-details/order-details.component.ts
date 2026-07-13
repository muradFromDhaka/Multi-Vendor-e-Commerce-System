import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderResponse, OrderStatus } from 'src/app/models/order.model';
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

  goBack(): void {
    this.router.navigate(['/orders']);
  }

}