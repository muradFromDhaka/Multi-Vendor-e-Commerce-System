import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderResponse } from 'src/app/models/order.model';
import { AdminVendorService } from '../../services/admin-vendor.service';

@Component({
  selector: 'app-vendor-orders',
  templateUrl: './vendor-orders.component.html',
  styleUrls: ['./vendor-orders.component.scss']
})
export class VendorOrdersComponent implements OnInit {

  vendorId!: number;

  orders: OrderResponse[] = [];

  page = 0;
  size = 10;

  totalPages = 0;
  totalElements = 0;

  loading = false;

  constructor(
    private route: ActivatedRoute,
    private adminVendorService: AdminVendorService
  ) {}

  ngOnInit(): void {

    this.vendorId = Number(
      this.route.snapshot.paramMap.get('id')
    );

    this.loadOrders();

  }

  loadOrders(): void {

    this.loading = true;

    this.adminVendorService
      .getVendorOrders(
        this.vendorId,
        this.page,
        this.size
      )
      .subscribe({

        next: (res) => {

          this.orders = res.content;
          this.totalPages = res.totalPages;
          this.totalElements = res.totalElements;

          this.loading = false;

        },

        error: err => {

          console.log(err);
          this.loading = false;

        }

      });

  }

  nextPage(): void {

    if (this.page < this.totalPages - 1) {

      this.page++;

      this.loadOrders();

    }

  }

  previousPage(): void {

    if (this.page > 0) {

      this.page--;

      this.loadOrders();

    }

  }

}