import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderResponse } from 'src/app/models/order.model';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.scss']
})
export class OrderDetailsComponent {

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
    this.router.navigate(['/orderList'])
  }
  
}
