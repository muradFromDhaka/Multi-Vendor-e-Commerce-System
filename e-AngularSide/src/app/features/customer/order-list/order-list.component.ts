import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { OrderResponse } from 'src/app/models/order.model';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.scss']
})
export class OrderListComponent {

  
  orders: OrderResponse[] = [];
  loading = true;

  constructor(
    private orderService: OrderService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadOrders();
  }


  

  loadOrders(): void {
    this.orderService.getMyOrders().subscribe({
      next: res => {
        this.orders = res.content;
        this.loading = false;
      },
      error: err => {
        console.error('Failed to load orders', err);
        this.loading = false;
      }
    });
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'PENDING': return 'badge bg-warning text-dark';
      case 'PAID': return 'badge bg-info';
      case 'SHIPPED': return 'badge bg-primary';
      case 'DELIVERED': return 'badge bg-success';
      case 'CANCELLED': return 'badge bg-danger';
      default: return 'badge bg-secondary';
    }
  }

}
