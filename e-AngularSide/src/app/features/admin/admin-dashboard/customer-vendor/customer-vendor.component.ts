import { Component, Input } from '@angular/core';
import { AdminDashboard } from '../../models/dashboard.model';

@Component({
  selector: 'app-customer-vendor',
  templateUrl: './customer-vendor.component.html',
  styleUrls: ['./customer-vendor.component.scss']
})
export class CustomerVendorComponent {

  @Input()
  dashboard!: AdminDashboard;
  
}
