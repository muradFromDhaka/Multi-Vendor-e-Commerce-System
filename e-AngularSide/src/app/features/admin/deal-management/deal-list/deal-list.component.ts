import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { DealService } from 'src/app/services/deal.service';
import { DealResponse } from 'src/app/models/deal.model';
import { PageResponse } from 'src/app/models/PageResponse';

@Component({
  selector: 'app-deal-list',
  templateUrl: './deal-list.component.html',
  styleUrls: ['./deal-list.component.scss']
})
export class DealListComponent implements OnInit {

  deals: DealResponse[] = [];

  loading = false;

  page = 0;

  size = 10;

  totalPages = 0;

  totalElements = 0;

  constructor(
    private dealService: DealService,
    private router: Router
  ) {}

  ngOnInit(): void {

    this.loadDeals();

  }

  loadDeals() {

    this.loading = true;

    this.dealService
      .getAllDeals(this.page, this.size)
      .subscribe({

        next: (response: PageResponse<DealResponse>) => {

          this.deals = response.content;

          this.totalPages = response.totalPages;

          this.totalElements = response.totalElements;

          this.loading = false;

        },

        error: err => {

          console.error(err);

          this.loading = false;

        }

      });

  }

  editDeal(id: number) {

    this.router.navigate([
      '/admin/deal-management/dealForm',
      id
    ]);

  }

  viewDeal(id: number) {

    this.router.navigate([
      '/admin/deal-management/dealDetails',
      id
    ]);

  }

  deleteDeal(id: number) {

    if (!confirm('Delete this deal?')) {

      return;

    }

    this.dealService
      .deleteDeal(id)
      .subscribe({

        next: () => {

          this.loadDeals();

        },

        error: err => console.error(err)

      });

  }

  previousPage() {

    if (this.page > 0) {

      this.page--;

      this.loadDeals();

    }

  }

  nextPage() {

    if (this.page < this.totalPages - 1) {

      this.page++;

      this.loadDeals();

    }

  }

  isExpired(endTime: string): boolean {

    return new Date(endTime) < new Date();

  }

}