import { Component, OnInit } from '@angular/core';

import {
  VendorDashboard,
  VendorPerformanceResponse
} from '../models/vendor-dashboard.model';

import { VendorDashboardService } from '../services/vendor-dashboard.service';

import { environment } from 'src/app/services/environments';


@Component({
  selector: 'app-vendor-dashboard',
  templateUrl: './vendor-dashboard.component.html',
  styleUrls: ['./vendor-dashboard.component.scss']
})
export class VendorDashboardComponent implements OnInit {

  // ===============================
  // Dashboard
  // ===============================

  dashboard?: VendorDashboard;

  baseImageUrl = environment.baseImageUrl;


  // ===============================
  // Loading
  // ===============================

  loading = false;

  performanceLoading = false;


  // ===============================
  // Date Range
  // ===============================

  fromDate = '';

  toDate = '';


  // ===============================
  // Performance
  // ===============================

  performance?: VendorPerformanceResponse;


  // ===============================
  // Constructor
  // ===============================

  constructor(
    private vendorDashboardService: VendorDashboardService
  ) {}


  // ===============================
  // Init
  // ===============================

  ngOnInit(): void {

    this.loadDashboard();

  }


  // ===============================
  // Load Dashboard
  // ===============================

  loadDashboard(): void {

    this.loading = true;

    this.vendorDashboardService
      .getDashboard()
      .subscribe({

        next: response => {

          this.dashboard = response;

          this.loading = false;

        },

        error: error => {

          console.error(
            'Failed to load vendor dashboard:',
            error
          );

          this.loading = false;

        }

      });

  }


  // ===============================
  // Load Performance
  // ===============================

  loadPerformance(): void {

    if (!this.fromDate || !this.toDate) {

      return;

    }

    if (this.fromDate > this.toDate) {

      alert(
        'From date cannot be after To date.'
      );

      return;

    }

    this.performanceLoading = true;

    this.vendorDashboardService
      .getPerformance(
        this.fromDate,
        this.toDate
      )
      .subscribe({

        next: response => {

          this.performance = response;

          this.performanceLoading = false;

        },

        error: err => {

          console.error(
            'Failed to load performance:',
            err
          );

          this.performanceLoading = false;

        }

      });

  }

}