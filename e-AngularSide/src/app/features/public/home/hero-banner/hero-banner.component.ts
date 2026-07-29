import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription, interval } from 'rxjs';
import { BannerResponse } from 'src/app/features/admin/models/banner.model';
import { BannerService } from 'src/app/features/admin/services/banner.service';
import { environment } from 'src/app/services/environments';

@Component({
  selector: 'app-hero-banner',
  templateUrl: './hero-banner.component.html',
  styleUrls: ['./hero-banner.component.scss']
})
export class HeroBannerComponent implements OnInit, OnDestroy {

  banners: BannerResponse[] = [];
  baseImageUrl = environment.baseImageUrl;

  currentBanner = 0;

  private sliderSubscription?: Subscription;

  constructor(
    private bannerService: BannerService
  ) {}

  ngOnInit(): void {

    this.loadBanners();

  }

  // =============================
  // Load Active Banners
  // =============================
  loadBanners(): void {

    this.bannerService.getActiveBanners(0, 10).subscribe({

      next: (response) => {

        this.banners = response.content;

        console.log('Active Banners:================', this.banners);

        if (this.banners.length > 1) {
          this.startSlider();
        }

      },

      error: (err) => {
        console.error(err);
      }

    });

  }

  // =============================
  // Auto Slider
  // =============================
  startSlider(): void {

    this.sliderSubscription = interval(2000).subscribe(() => {

      this.nextBanner();

    });

  }

  // =============================
  // Next
  // =============================
  nextBanner(): void {

    if (!this.banners.length) return;

    this.currentBanner =
      (this.currentBanner + 1) % this.banners.length;

  }

  // =============================
  // Previous
  // =============================
  previousBanner(): void {

    if (!this.banners.length) return;

    this.currentBanner =
      (this.currentBanner - 1 + this.banners.length)
      % this.banners.length;

  }

  // =============================
  // Indicator Click
  // =============================
  goToBanner(index: number): void {

    this.currentBanner = index;

  }

  ngOnDestroy(): void {

    this.sliderSubscription?.unsubscribe();

  }

}