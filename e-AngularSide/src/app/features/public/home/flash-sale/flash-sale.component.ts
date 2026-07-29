import { Component, OnDestroy, OnInit } from '@angular/core';
import { interval, Subscription } from 'rxjs';

import { DealService } from 'src/app/services/deal.service';
import { DealResponse } from 'src/app/models/deal.model';
import { environment } from 'src/app/services/environments';

@Component({
  selector: 'app-flash-sale',
  templateUrl: './flash-sale.component.html',
  styleUrls: ['./flash-sale.component.scss']
})
export class FlashSaleComponent implements OnInit, OnDestroy {

  deals: DealResponse[] = [];

  baseImageUrl = environment.baseImageUrl;
  hours = 2;
  minutes = 30;
  seconds = 45;

  private timerSubscription?: Subscription;

  loading = false;

  constructor(
    private dealService: DealService
  ) {}

  ngOnInit(): void {

    this.loadDeals();

    this.startTimer();

  }

  // ============================
  // Load Active Deals
  // ============================
  loadDeals() {

    this.loading = true;

    this.dealService
      .getActiveDeals(0, 8)
      .subscribe({

        next: (response) => {

          this.deals = response.content;

          // console.log("load deals------",this.deals[0]);
          // console.log("load dealsImage------",this.baseImageUrl + this.deals[0].productImage);

          this.loading = false;

        },

        error: err => {

          console.error(err);

          this.loading = false;

        }

      });

  }

  // ============================
  // Countdown
  // ============================
  startTimer() {

    this.timerSubscription = interval(1000).subscribe(() => {

      if (this.seconds > 0) {

        this.seconds--;

      }

      else if (this.minutes > 0) {

        this.minutes--;
        this.seconds = 59;

      }

      else if (this.hours > 0) {

        this.hours--;
        this.minutes = 59;
        this.seconds = 59;

      }

    });

  }

  ngOnDestroy(): void {

    this.timerSubscription?.unsubscribe();

  }

}