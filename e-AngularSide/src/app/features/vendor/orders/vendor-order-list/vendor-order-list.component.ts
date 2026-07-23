import { Component, OnInit } from '@angular/core';
import { VendorOrderListResponse, VendorOrderPage, VendorOrderStatus } from '../../models/vendorOrder.model';
import { VendorOrderService } from '../../services/vendor-order.service';

@Component({
  selector: 'app-vendor-order-list',
  templateUrl: './vendor-order-list.component.html',
  styleUrls: ['./vendor-order-list.component.scss']
})
export class VendorOrderListComponent implements OnInit {

  orders: VendorOrderListResponse[] = [];

  loading = false;
  error = '';

  page = 0;
  size = 10;
  totalElements = 0;

  displayedColumns: string[] = [
    'orderNumber',
    'customerName',
    'totalItems',
    'orderAmount',
    'status',
    'createdAt',
    'action'
  ];

  constructor(
    private vendorOrderService: VendorOrderService
  ) {}

  ngOnInit(): void {
    this.loadOrders();
  }

  loadOrders(): void {

    this.loading = true;
    this.error = '';

    this.vendorOrderService
      .getVendorOrders(this.page, this.size)
      .subscribe({

        next: (response: VendorOrderPage) => {

          this.orders = response.content;
          this.totalElements = response.totalElements;

          this.loading = false;

        },

        error: (err) => {

          console.error(err);

          this.error = 'Failed to load orders.';
          this.loading = false;

        }

      });

  }

  onPageChange(event: any): void {

    this.page = event.pageIndex;
    this.size = event.pageSize;

    this.loadOrders();

  }

  getStatusClass(status: VendorOrderStatus): string {

    switch (status) {

      case VendorOrderStatus.PENDING:
        return 'pending';

      case VendorOrderStatus.CONFIRMED:
        return 'confirmed';

      case VendorOrderStatus.PROCESSING:
        return 'processing';

      case VendorOrderStatus.PACKED:
        return 'packed';

      case VendorOrderStatus.SHIPPED:
        return 'shipped';

      case VendorOrderStatus.DELIVERED:
        return 'delivered';

      case VendorOrderStatus.CANCELLED:
        return 'cancelled';

      case VendorOrderStatus.RETURNED:
        return 'returned';

      default:
        return '';
    }

  }

  previousPage(): void {

  if (this.page > 0) {

    this.page--;
    this.loadOrders();

  }

}

nextPage(): void {

  if ((this.page + 1) * this.size < this.totalElements) {

    this.page++;
    this.loadOrders();

  }

}

}