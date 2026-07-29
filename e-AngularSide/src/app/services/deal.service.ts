import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from './environments';
import {
  DealRequest,
  DealResponse,
  DealPageResponse
} from '../models/deal.model';

@Injectable({
  providedIn: 'root'
})
export class DealService {

  private readonly baseUrl =
    `${environment.apiUrl}/admin/deals`;

  constructor(
    private http: HttpClient
  ) { }

  // =========================================
  // Admin - Get All Deals
  // =========================================
  getAllDeals(
    page = 0,
    size = 10,
    sortBy = 'createdAt',
    sortDir = 'desc'
  ): Observable<DealPageResponse> {

    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sortBy', sortBy)
      .set('sortDir', sortDir);

    return this.http.get<DealPageResponse>(
      this.baseUrl,
      { params }
    );

  }

  // =========================================
  // Get Deal By Id
  // =========================================
  getDealById(id: number): Observable<DealResponse> {

    return this.http.get<DealResponse>(
      `${this.baseUrl}/${id}`
    );

  }

  // =========================================
  // Create Deal
  // =========================================
  createDeal(
    request: DealRequest
  ): Observable<DealResponse> {

    return this.http.post<DealResponse>(
      this.baseUrl,
      request
    );

  }

  // =========================================
  // Update Deal
  // =========================================
  updateDeal(
    id: number,
    request: DealRequest
  ): Observable<DealResponse> {

    return this.http.put<DealResponse>(
      `${this.baseUrl}/${id}`,
      request
    );

  }

  // =========================================
  // Delete Deal
  // =========================================
  deleteDeal(id: number): Observable<string> {

    return this.http.delete(
      `${this.baseUrl}/${id}`,
      {
        responseType: 'text'
      }
    );

  }

  // =========================================
  // Public - Active Deals
  // =========================================
  getActiveDeals(
    page = 0,
    size = 10,
    sortBy = 'createdAt',
    sortDir = 'desc'
  ): Observable<DealPageResponse> {

    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sortBy', sortBy)
      .set('sortDir', sortDir);

    return this.http.get<DealPageResponse>(
      `${this.baseUrl}/active`,
      { params }
    );

  }

  // =========================================
  // Public - Active Deal By Product
  // =========================================
  getActiveDealByProduct(
    productId: number
  ): Observable<DealResponse> {

    return this.http.get<DealResponse>(
      `${this.baseUrl}/product/${productId}`
    );

  }

}