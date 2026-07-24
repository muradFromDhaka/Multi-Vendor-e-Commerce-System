import { Component, Input } from '@angular/core';
import { AdminDashboard } from '../../models/dashboard.model';

@Component({
  selector: 'app-engagement-analytics',
  templateUrl: './engagement-analytics.component.html',
  styleUrls: ['./engagement-analytics.component.scss']
})
export class EngagementAnalyticsComponent {

  @Input()
  dashboard!: AdminDashboard;
  
}
