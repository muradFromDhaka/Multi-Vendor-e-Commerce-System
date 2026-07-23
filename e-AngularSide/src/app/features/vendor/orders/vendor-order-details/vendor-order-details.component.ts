import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { finalize } from 'rxjs';
import { UpdateVendorOrderStatusRequest, VendorOrderDetailsResponse, VendorOrderStatus } from '../../models/vendorOrder.model';
import { VendorOrderService } from '../../services/vendor-order.service';
import { PaymentStatus } from 'src/app/models/order.model';

@Component({
  selector: 'app-vendor-order-details',
  templateUrl: './vendor-order-details.component.html',
  styleUrls: ['./vendor-order-details.component.scss']
})
export class VendorOrderDetailsComponent implements OnInit {

  vendorOrder?: VendorOrderDetailsResponse;

  loading = false;

  updating = false;

  errorMessage = '';

  vendorOrderId!: number;

  selectedStatus!: VendorOrderStatus;

  readonly vendorOrderStatuses = Object.values(VendorOrderStatus);

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private vendorOrderService: VendorOrderService
  ) {
  }

  ngOnInit(): void {

    this.vendorOrderId = Number(
      this.route.snapshot.paramMap.get('id')
    );

    this.loadVendorOrderDetails();

  }

  loadVendorOrderDetails(): void {

    this.loading = true;

    this.vendorOrderService
      .getVendorOrderDetails(this.vendorOrderId)
      .pipe(
        finalize(() => this.loading = false)
      )
      .subscribe({

        next: (response) => {

          this.vendorOrder = response;

          this.selectedStatus =
            response.vendorOrderStatus;

        },

        error: (err) => {

          console.error(err);

          this.errorMessage =
            'Failed to load vendor order details.';

        }

      });

  }

  updateVendorOrderStatus(
    status: VendorOrderStatus
  ): void {

    if (!this.vendorOrder) {

      return;

    }

    if (status === this.vendorOrder.vendorOrderStatus) {

      return;

    }

    this.updating = true;

    const request: UpdateVendorOrderStatusRequest = {

      vendorOrderStatus: status

    };

    this.vendorOrderService
      .updateVendorOrderStatus(
        this.vendorOrderId,
        request
      )
      .pipe(
        finalize(() => this.updating = false)
      )
      .subscribe({

        next: (response) => {

          this.vendorOrder = response;

          this.selectedStatus =
            response.vendorOrderStatus;

        },

        error: (err) => {

          console.error(err);

          alert(err.error?.message ??
            'Failed to update vendor order.');

        }

      });

  }

  goBack(): void {

    this.router.navigate([
      '/vendor/orders'
    ]);

  }

  getVendorStatusClass(
    status?: VendorOrderStatus
  ): string {

    switch (status) {

      case VendorOrderStatus.PENDING:
        return 'bg-secondary';

      case VendorOrderStatus.CONFIRMED:
        return 'bg-primary';

      case VendorOrderStatus.PROCESSING:
        return 'bg-warning text-dark';

      case VendorOrderStatus.PACKED:
        return 'bg-info text-dark';

      case VendorOrderStatus.SHIPPED:
        return 'bg-dark';

      case VendorOrderStatus.DELIVERED:
        return 'bg-success';

      case VendorOrderStatus.CANCELLED:
        return 'bg-danger';

      case VendorOrderStatus.RETURNED:
        return 'bg-secondary';

      default:
        return 'bg-light text-dark';

    }

  }

  vendorOrderStatus: VendorOrderStatus[] = [

  VendorOrderStatus.PENDING,

  VendorOrderStatus.CONFIRMED,

  VendorOrderStatus.PROCESSING,

  VendorOrderStatus.PACKED,

  VendorOrderStatus.SHIPPED,

  VendorOrderStatus.DELIVERED

];

isCompleted(status: VendorOrderStatus): boolean {

  if (!this.vendorOrder) {

    return false;
  }

  const current =
    this.vendorOrderStatuses.indexOf(
      this.vendorOrder.vendorOrderStatus
    );

  const target =
    this.vendorOrderStatuses.indexOf(status);

  return target < current;

}



  getPaymentStatusClass(
    status?: PaymentStatus
  ): string {

    switch (status) {

      case PaymentStatus.PENDING:
        return 'bg-warning text-dark';

      case PaymentStatus.PAID:
        return 'bg-success';

      case PaymentStatus.FAILED:
        return 'bg-danger';

      case PaymentStatus.REFUNDED:
        return 'bg-info text-dark';

      case PaymentStatus.CANCELLED:
        return 'bg-secondary';

      default:
        return 'bg-light text-dark';

    }

  }

}