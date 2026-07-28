import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { PageResponse } from 'src/app/models/PageResponse';
import { ReviewResponse } from 'src/app/models/review.model';
import { ReviewService } from 'src/app/shared/services/review.service';

@Component({
  selector: 'app-review-list',
  templateUrl: './review-list.component.html',
  styleUrls: ['./review-list.component.scss']
})
export class ReviewListComponent implements OnChanges {

  @Input() productId!: number;

  reviews: ReviewResponse[] = [];

  page = 0;
  size = 5;

  totalPages = 0;
  totalElements = 0;

  loading = false;

  constructor(
    private reviewService: ReviewService
  ) {}

  ngOnChanges(changes: SimpleChanges): void {

    if (changes['productId'] && this.productId) {
      this.page = 0;
      this.loadReviews();
    }

  }

  loadReviews(): void {

    this.loading = true;

    this.reviewService
      .getProductReviews(this.productId, this.page, this.size)
      .subscribe({

        next: (response: PageResponse<ReviewResponse>) => {

          this.reviews = response.content;

          this.totalPages = response.totalPages;
          this.totalElements = response.totalElements;

          this.loading = false;
        },

        error: () => {

          this.loading = false;

        }

      });

  }

  previousPage(): void {

    if (this.page > 0) {

      this.page--;

      this.loadReviews();

    }

  }

  nextPage(): void {

    if (this.page < this.totalPages - 1) {

      this.page++;

      this.loadReviews();

    }

  }

}