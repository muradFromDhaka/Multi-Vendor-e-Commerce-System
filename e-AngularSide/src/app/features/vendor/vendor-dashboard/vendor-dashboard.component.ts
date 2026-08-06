import { Component, OnInit } from '@angular/core';
import { VendorDashboard } from '../models/vendor-dashboard.model';
import { VendorDashboardService } from '../services/vendor-dashboard.service';
import { environment } from 'src/app/services/environments';

@Component({
  selector: 'app-vendor-dashboard',
  templateUrl: './vendor-dashboard.component.html',
  styleUrls: ['./vendor-dashboard.component.scss']
})
export class VendorDashboardComponent
implements OnInit {

  dashboard?: VendorDashboard;
  baseImageUrl = environment.baseImageUrl;

  loading = false;

  constructor(
    private dashboardService: VendorDashboardService
  ) { }

  ngOnInit(): void {

    this.loadDashboard();

  }

  loadDashboard(): void {

    this.loading = true;

    this.dashboardService
      .getDashboard()
      .subscribe({

        next: response => {

          this.dashboard = response;

          this.loading = false;

        },

        error: error => {

          console.error(error);

          this.loading = false;

        }

      });

  }

}