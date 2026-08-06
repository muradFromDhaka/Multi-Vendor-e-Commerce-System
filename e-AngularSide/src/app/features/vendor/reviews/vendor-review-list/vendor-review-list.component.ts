import { Component, OnInit } from '@angular/core';
import { PageResponse } from 'src/app/models/PageResponse';
import {
  ProductReviewSummary,
  ReviewResponse
} from 'src/app/models/review.model';
import { ReviewService } from 'src/app/shared/services/review.service';

@Component({
  selector: 'app-vendor-review-list',
  templateUrl: './vendor-review-list.component.html',
  styleUrls: ['./vendor-review-list.component.scss']
})
export class VendorReviewListComponent implements OnInit {

  // ===============================
  // Data
  // ===============================

  reviews: ReviewResponse[] = [];

  summary: ProductReviewSummary = {
    averageRating: 0,
    totalReviews: 0,
    fiveStar: 0,
    fourStar: 0,
    threeStar: 0,
    twoStar: 0,
    oneStar: 0
  };

  // ===============================
  // Loading
  // ===============================

  loading = false;

  // ===============================
  // Pagination
  // ===============================

  page = 0;

  size = 10;

  totalPages = 0;

  totalElements = 0;

  first = true;

  last = false;

  // ===============================
  // Constructor
  // ===============================

  constructor(
    private reviewService: ReviewService
  ) {}

  // ===============================
  // Init
  // ===============================

  ngOnInit(): void {

    this.loadSummary();

    this.loadReviews();

  }

  // ===============================
  // Summary
  // ===============================

  loadSummary(): void {

    this.reviewService
      .getVendorReviewSummary()
      .subscribe({

        next: response => {

          this.summary = response;

        },

        error: err => console.error(err)

      });

  }

  // ===============================
  // Reviews
  // ===============================

  loadReviews(): void {

    this.loading = true;

    this.reviewService
      .getVendorReviews(
        this.page,
        this.size
      )
      .subscribe({

        next: (response: PageResponse<ReviewResponse>) => {

          this.reviews = response.content;

          this.totalPages = response.totalPages;

          this.totalElements = response.totalElements;

          this.first = response.first;

          this.last = response.last;

          this.loading = false;

        },

        error: err => {

          console.error(err);

          this.loading = false;

        }

      });

  }

  // ===============================
  // Pagination
  // ===============================

  nextPage(): void {

    if (!this.last) {

      this.page++;

      this.loadReviews();

    }

  }

  previousPage(): void {

    if (!this.first) {

      this.page--;

      this.loadReviews();

    }

  }

  goToPage(page: number): void {

    this.page = page;

    this.loadReviews();

  }

  // ===============================
  // Rating Distribution
  // ===============================

  getPercentage(count: number): number {

    if (!this.summary.totalReviews) {

      return 0;

    }

    return (count / this.summary.totalReviews) * 100;

  }

  // ===============================
  // Stars
  // ===============================

  getStars(rating: number): string[] {

    const stars: string[] = [];

    for (let i = 1; i <= 5; i++) {

      stars.push(i <= rating ? '★' : '☆');

    }

    return stars;

  }

  getAverageStars(): string[] {

    const stars: string[] = [];

    const rounded = Math.round(
      this.summary.averageRating
    );

    for (let i = 1; i <= 5; i++) {

      stars.push(i <= rounded ? '★' : '☆');

    }

    return stars;

  }

  // ===============================
  // Page Numbers
  // ===============================

  get pageNumbers(): number[] {

    return Array(this.totalPages)
      .fill(0)
      .map((x, i) => i);

  }

}