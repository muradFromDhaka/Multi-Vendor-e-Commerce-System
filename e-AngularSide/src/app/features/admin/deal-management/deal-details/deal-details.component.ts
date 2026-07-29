import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { DealService } from 'src/app/services/deal.service';
import { DealResponse } from 'src/app/models/deal.model';

@Component({
  selector: 'app-deal-details',
  templateUrl: './deal-details.component.html',
  styleUrls: ['./deal-details.component.scss']
})
export class DealDetailsComponent implements OnInit {

  deal!: DealResponse;

  loading = true;

  constructor(
    private dealService: DealService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {

    const id = Number(
      this.route.snapshot.paramMap.get('id')
    );

    if (id) {
      this.loadDeal(id);
    }

  }

  loadDeal(id: number): void {

    this.dealService
      .getDealById(id)
      .subscribe({

        next: (response) => {

          this.deal = response;

          this.loading = false;

        },

        error: (err) => {

          console.error(err);

          this.loading = false;

        }

      });

  }

  editDeal(): void {

    this.router.navigate([
      '/admin/deal-management/dealForm',
      this.deal.id
    ]);

  }

  back(): void {

    this.router.navigate([
      '/admin/deal-management/deal'
    ]);

  }

  isExpired(): boolean {

    return new Date(this.deal.endTime) < new Date();

  }

}