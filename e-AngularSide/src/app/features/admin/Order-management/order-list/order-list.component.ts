import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AdminOrderService } from '../../services/admin-order.service';

import {
  AdminOrderListResponse,
  AdminOrderPage
} from '../../models/AdminOrder.model';

import {
  OrderStatus,
  PaymentStatus
} from 'src/app/models/order.model';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.scss']
})
export class OrderListComponent implements OnInit {

  orders: AdminOrderListResponse[] = [];

  loading = false;

  page = 0;
  size = 10;
  totalElements = 0;

  searchText = '';

  // orderStatuses = Object.values(OrderStatus);
  // paymentStatuses = Object.values(PaymentStatus);

  constructor(
    private adminOrderService: AdminOrderService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadOrders();
  }

  loadOrders(): void {

    this.loading = true;

    this.adminOrderService
      .getAllOrders(this.page, this.size)
      .subscribe({

        next: (response: AdminOrderPage) => {

          this.orders = response.content;
          this.totalElements = response.totalElements;
          this.loading = false;

        },

        error: () => {

          this.loading = false;

        }

      });

  }

  pageChanged(page: number): void {

    this.page = page;

    this.loadOrders();

  }

  search(): void {

    const keyword = this.searchText.trim();

    if (!keyword) {

      this.loadOrders();

      return;

    }

    if (!isNaN(Number(keyword))) {

      this.adminOrderService
        .getOrderById(Number(keyword))
        .subscribe({

          next: res => {

            this.router.navigate([
              '/admin/orders',
              res.id
            ]);

          },

          error: () => {

            this.orders = [];

          }

        });

    } else {

      this.adminOrderService
        .getOrderByOrderNumber(keyword)
        .subscribe({

          next: res => {

            this.router.navigate([
              '/admin/orders',
              res.id
            ]);

          },

          error: () => {

            this.orders = [];

          }

        });

    }

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



  viewOrder(orderId: number): void {

    this.router.navigate([
      '/admin/orders',
      orderId
    ]);

  }

}