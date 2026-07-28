import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { ReviewRequest, ReviewResponse } from 'src/app/models/review.model';
import { ReviewService } from 'src/app/shared/services/review.service';


@Component({
  selector: 'app-review-form',
  templateUrl: './review-form.component.html',
  styleUrls: ['./review-form.component.scss']
})
export class ReviewFormComponent implements OnChanges {

  @Input() productId!: number;

  // null = Create Mode
  // not null = Update Mode
  @Input() review?: ReviewResponse | null;

  @Output() reviewSaved = new EventEmitter<void>();

  loading = false;

  hoveredRating = 0;

  form: ReviewRequest = {
    productId: 0,
    rating: 5,
    comment: ''
  };

  constructor(
    private reviewService: ReviewService
  ) {}

  ngOnChanges(changes: SimpleChanges): void {

    this.form.productId = this.productId;

    if (this.review) {

      this.form.rating = this.review.rating;
      this.form.comment = this.review.comment;

    } else {

      this.form.rating = 5;
      this.form.comment = '';

    }

  }

  submit(): void {

    if (!this.form.comment.trim()) {
      return;
    }

    this.loading = true;

    if (this.review) {

      this.reviewService
        .updateReview(this.review.id, this.form)
        .subscribe({

          next: () => {

            this.loading = false;

            this.reviewSaved.emit();

          },

          error: () => {

            this.loading = false;

          }

        });

    } else {

      this.reviewService
        .createReview(this.form)
        .subscribe({

          next: () => {

            this.loading = false;

            this.resetForm();

            this.reviewSaved.emit();

            alert("Review submitted successfully");

          },

          error: (err) => {

            alert(err.error.message);

            this.loading = false;

          }

        });

    }

  }

  selectRating(star: number): void {

    this.form.rating = star;

  }

  resetForm(): void {

    this.form.rating = 5;
    this.form.comment = '';

  }

}