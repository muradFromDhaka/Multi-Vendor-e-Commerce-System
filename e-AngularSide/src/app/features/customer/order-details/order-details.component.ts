import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderResponse } from 'src/app/models/order.model';
import { environment } from 'src/app/services/environments';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.scss']
})
export class OrderDetailsComponent {

  baseImageUrl = environment.baseImageUrl;

  order!: OrderResponse;

  constructor(
    private acRouter: ActivatedRoute,
    private router: Router,
    private orderService: OrderService,
     ) {}

  ngOnInit() {
    const orderId = this.acRouter.snapshot.params['id'];
    this.orderService.getOrder(orderId).subscribe(order => this.order = order);
  }

  close(){
    this.router.navigate(['/customer/orders'])
  }


  timelineSteps = [
  'PENDING',
  'PROCESSING',
  'SHIPPED',
  'DELIVERED'
];

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
