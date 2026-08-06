import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../services/environments';
import { ProductReviewSummary, ReviewRequest, ReviewResponse } from '../../models/review.model';
import { PageResponse } from '../../models/PageResponse';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  private readonly apiUrl = `${environment.apiUrl}/reviews`;

  constructor(
    private http: HttpClient
  ) {}

  // ===========================
  // Create Review
  // ===========================
  createReview(
    request: ReviewRequest
  ): Observable<ReviewResponse> {

    return this.http.post<ReviewResponse>(
      this.apiUrl,
      request
    );
  }

  // ===========================
  // Update Review
  // ===========================
  updateReview(
    reviewId: number,
    request: ReviewRequest
  ): Observable<ReviewResponse> {

    return this.http.put<ReviewResponse>(
      `${this.apiUrl}/${reviewId}`,
      request
    );
  }

  // ===========================
  // Delete Review
  // ===========================
  deleteReview(
    reviewId: number
  ): Observable<string> {

    return this.http.delete(
      `${this.apiUrl}/${reviewId}`,
      {
        responseType: 'text'
      }
    );
  }

  // ===========================
  // Product Reviews (Paginated)
  // ===========================
  getProductReviews(
    productId: number,
    page: number = 0,
    size: number = 10,
    sort: string = 'createdAt,desc'
  ): Observable<PageResponse<ReviewResponse>> {

    return this.http.get<PageResponse<ReviewResponse>>(
      `${this.apiUrl}/product/${productId}?page=${page}&size=${size}&sort=${sort}`
    );
  }

  // ===========================
  // Product Review Summary
  // ===========================
  getProductReviewSummary(
    productId: number
  ): Observable<ProductReviewSummary> {

    return this.http.get<ProductReviewSummary>(
      `${this.apiUrl}/product/${productId}/summary`
    );
  }

  // ===========================
  // Logged-in User Reviews
  // ===========================
  getMyReviews(): Observable<ReviewResponse[]> {

    return this.http.get<ReviewResponse[]>(
      `${this.apiUrl}/me`
    );
  }


  getLatestReviews(): Observable<ReviewResponse[]> {
  return this.http.get<ReviewResponse[]>(
    `${this.apiUrl}/latest`
  );
}


// ===========================
// Vendor Reviews
// ===========================
getVendorReviews(
  page: number = 0,
  size: number = 10,
  sort: string = 'createdAt,desc'
): Observable<PageResponse<ReviewResponse>> {

  return this.http.get<PageResponse<ReviewResponse>>(
    `${this.apiUrl}/vendor?page=${page}&size=${size}&sort=${sort}`
  );
}

// ===========================
// Vendor Review Summary
// ===========================
getVendorReviewSummary(): Observable<ProductReviewSummary> {

  return this.http.get<ProductReviewSummary>(
    `${this.apiUrl}/vendor/summary`
  );

}


}