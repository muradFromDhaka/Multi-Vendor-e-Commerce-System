import { Component } from '@angular/core';

@Component({
  selector: 'app-dashboard-header',
  templateUrl: './dashboard-header.component.html',
  styleUrls: ['./dashboard-header.component.scss']
})
export class DashboardHeaderComponent {

  selectedRange = 'THIS_YEAR';

  dateRanges = [
    { label: 'Today', value: 'TODAY' },
    { label: 'Last 7 Days', value: 'LAST_7_DAYS' },
    { label: 'Last 30 Days', value: 'LAST_30_DAYS' },
    { label: 'This Month', value: 'THIS_MONTH' },
    { label: 'This Year', value: 'THIS_YEAR' }
  ];

}