import { Component, Input } from '@angular/core';
import { AdminDashboard } from '../../models/dashboard.model';

@Component({
  selector: 'app-dashboard-overview',
  templateUrl: './dashboard-overview.component.html',
  styleUrls: ['./dashboard-overview.component.scss']
})
export class DashboardOverviewComponent {

    @Input()

  dashboard!: AdminDashboard;
  
}
