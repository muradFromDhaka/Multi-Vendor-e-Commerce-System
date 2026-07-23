import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderResponse } from 'src/app/models/order.model';
import { environment } from 'src/app/services/environments';
import { OrderService } from 'src/app/services/order.service';
import { VendorOrderStatus } from '../../vendor/models/vendorOrder.model';
import { CustomerPaymentService } from '../services/customer-payment.service';
import { PaymentResponse } from 'src/app/shared/models/payment.model';

declare var bootstrap: any;

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.scss']
})
export class OrderDetailsComponent {

  baseImageUrl = environment.baseImageUrl;

  order!: OrderResponse;
  payment?: PaymentResponse;

  VendorOrderStatus = VendorOrderStatus;

  constructor(
    private acRouter: ActivatedRoute,
    private router: Router,
    private orderService: OrderService,
    private customerPaymentService: CustomerPaymentService
     ) {}

  ngOnInit() {
    const orderId = this.acRouter.snapshot.params['id'];


    // this.orderService.getOrder(orderId).subscribe(order => this.order = order);

    this.orderService.getOrder(orderId).subscribe({
    next: (order) => {
      this.order = order;

      console.log(this.order);
      console.log(this.order.vendorOrders);
    },

    error: (err) => {
      console.error(err);
    }
  });

  }

  close(){
    this.router.navigate(['/customer/orders'])
  }


  timelineSteps = [
  'PENDING',
  'PROCESSING',
  'SHIPPED',
  'DELIVERED',
  'RETURNED'
];

 viewPaymentDetails(): void {

    this.customerPaymentService
      .getPaymentByOrderId(this.order.id)
      .subscribe({

        next: (response) => {

          this.payment = response;

          const modal = new bootstrap.Modal(
            document.getElementById('paymentModal')
          );

          modal.show();

        },

        error: err => {

          console.error(err);

          alert("Unable to load payment information.");

        }

      });

  }


getCurrentStep(): number {

  if (!this.order) {
    return -1;
  }

  return this.timelineSteps.indexOf(this.order.orderStatus);

}


cancelOrder() {

  if (!confirm('Are you sure you want to cancel this order?')) {
    return;
  }

  this.orderService.cancelOrder(this.order.id)
      .subscribe({

        next: (response) => {

          this.order = response;

          alert('Order cancelled successfully.');

        },

        error: (err) => {

          alert(err.error.message);

        }

      });

}


getVendorStatusClass(status: VendorOrderStatus): string {

  switch (status) {

    case VendorOrderStatus.PENDING:
      return 'bg-warning';

    case VendorOrderStatus.CONFIRMED:
      return 'bg-info';

    case VendorOrderStatus.PROCESSING:
      return 'bg-primary';

    case VendorOrderStatus.SHIPPED:
      return 'bg-primary';

    case VendorOrderStatus.DELIVERED:
      return 'bg-success';

    case VendorOrderStatus.CANCELLED:
      return 'bg-danger';

    case VendorOrderStatus.RETURNED:
      return 'bg-secondary';

    default:
      return 'bg-secondary';
  }
}

  downloadInvoice() {

  this.orderService
      .downloadInvoice(this.order.id)
      .subscribe(blob => {

        const url = window.URL.createObjectURL(blob);

        const a = document.createElement('a');

        a.href = url;

        a.download = `Invoice-${this.order.orderNumber}.pdf`;

        a.click();

        window.URL.revokeObjectURL(url);
      });
}
  
}
