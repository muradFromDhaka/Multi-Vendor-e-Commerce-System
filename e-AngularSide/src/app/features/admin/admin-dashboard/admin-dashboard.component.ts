import { Component, OnInit } from '@angular/core';
import { AdminDashboard } from '../models/dashboard.model';
import { AdminDashboardService } from '../services/admin-dashboard.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss']
})
export class AdminDashboardComponent {

   dashboard?: AdminDashboard;

  loading = true;

  constructor(
    private adminDashboardService: AdminDashboardService
  ) { }

  ngOnInit(): void {
    this.loadDashboard();
  }

  loadDashboard() {

    this.adminDashboardService.getDashboard().subscribe({

      next: (res) => {
        this.dashboard = res;
        this.loading = false;
      },

      error: () => {
        this.loading = false;
      }

    });

  }
  
}