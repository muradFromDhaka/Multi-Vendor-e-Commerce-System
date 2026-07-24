import { Component, Input } from '@angular/core';
import { AdminDashboard } from '../../models/dashboard.model';

@Component({
  selector: 'app-revenue-analytics',
  templateUrl: './revenue-analytics.component.html',
  styleUrls: ['./revenue-analytics.component.scss']
})
export class RevenueAnalyticsComponent {

  @Input()
  dashboard!: AdminDashboard;
  
}
