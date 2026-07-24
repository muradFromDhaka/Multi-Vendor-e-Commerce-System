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

  loading = false;

  error = '';

  constructor(
    private adminDashboardService: AdminDashboardService
  ) { }

  ngOnInit(): void {
    this.loadDashboard();
  }

  loadDashboard(): void {

    this.loading = true;

    this.error = '';

    this.adminDashboardService.getDashboard().subscribe({

      next: (response) => {

        this.dashboard = response;

        this.loading = false;

      },

      error: () => {

        this.error = 'Failed to load dashboard.';

        this.loading = false;

      }

    });

  }
  
}