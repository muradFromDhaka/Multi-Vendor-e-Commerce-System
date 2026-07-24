import { Component, Input } from '@angular/core';
import { AdminDashboard } from '../../models/dashboard.model';

@Component({
  selector: 'app-product-analytics',
  templateUrl: './product-analytics.component.html',
  styleUrls: ['./product-analytics.component.scss']
})
export class ProductAnalyticsComponent {

  @Input()
    dashboard!: AdminDashboard;
    
}
