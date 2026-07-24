import { Component, Input } from '@angular/core';
import { AdminDashboard } from '../../models/dashboard.model';

@Component({
  selector: 'app-finance-payment',
  templateUrl: './finance-payment.component.html',
  styleUrls: ['./finance-payment.component.scss']
})
export class FinancePaymentComponent {

  @Input()
  dashboard!: AdminDashboard;
  
}
