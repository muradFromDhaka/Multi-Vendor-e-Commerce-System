import {
  Component,
  Input,
  OnChanges,
  SimpleChanges
} from '@angular/core';
import { ProductReviewSummary } from 'src/app/models/review.model';
import { ReviewService } from 'src/app/shared/services/review.service';


@Component({
  selector: 'app-review-summary',
  templateUrl: './review-summary.component.html',
  styleUrls: ['./review-summary.component.scss']
})
export class ReviewSummaryComponent implements OnChanges {

  @Input() productId!: number;

  summary?: ProductReviewSummary;

  loading = false;

  constructor(
    private reviewService: ReviewService
  ) {}

  ngOnChanges(changes: SimpleChanges): void {

    if (changes['productId'] && this.productId) {
      this.loadSummary();
    }

  }

  loadSummary(): void {

    this.loading = true;

    this.reviewService
      .getProductReviewSummary(this.productId)
      .subscribe({

        next: (response) => {

          this.summary = response;
          this.loading = false;

        },

        error: () => {

          this.loading = false;

        }

      });

  }

  getPercentage(count: number): number {

    if (!this.summary || this.summary.totalReviews === 0) {
      return 0;
    }

    return (count / this.summary.totalReviews) * 100;

  }

}