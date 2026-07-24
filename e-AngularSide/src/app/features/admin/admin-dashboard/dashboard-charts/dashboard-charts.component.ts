import { Component, Input } from '@angular/core';
import { AdminDashboard } from '../../models/dashboard.model';

@Component({
  selector: 'app-dashboard-charts',
  templateUrl: './dashboard-charts.component.html',
  styleUrls: ['./dashboard-charts.component.scss']
})
export class DashboardChartsComponent {

  @Input()
  dashboard!: AdminDashboard;
  
}
