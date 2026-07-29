import { Component, OnInit } from '@angular/core';
import { ReviewService } from 'src/app/shared/services/review.service';
import { ReviewResponse } from 'src/app/models/review.model';

@Component({
  selector: 'app-customer-reviews',
  templateUrl: './customer-reviews.component.html',
  styleUrls: ['./customer-reviews.component.scss']
})
export class CustomerReviewsComponent implements OnInit {

  reviews: ReviewResponse[] = [];

  loading = false;

  constructor(
    private reviewService: ReviewService
  ) {}

  ngOnInit(): void {
    this.loadReviews();
  }

  loadReviews(): void {

  this.reviewService.getLatestReviews().subscribe({

    next: (reviews) => {
      this.reviews = reviews;

      // console.log("What Our Customers Say======================", this.reviews);
    },

    error: console.error

  });

}

}