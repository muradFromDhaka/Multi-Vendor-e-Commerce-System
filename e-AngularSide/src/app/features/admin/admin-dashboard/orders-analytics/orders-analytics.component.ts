import { Component, Input } from '@angular/core';
import { AdminDashboard } from '../../models/dashboard.model';

@Component({
  selector: 'app-orders-analytics',
  templateUrl: './orders-analytics.component.html',
  styleUrls: ['./orders-analytics.component.scss']
})
export class OrdersAnalyticsComponent {

  @Input()
  dashboard!: AdminDashboard;
  
}
