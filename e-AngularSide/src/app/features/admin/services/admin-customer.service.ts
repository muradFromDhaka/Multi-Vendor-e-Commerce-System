import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from 'src/app/services/environments';
import { CustomerStatistics, UserResponse } from 'src/app/models/customer.model';
import { PageResponse } from 'src/app/models/PageResponse';

@Injectable({
  providedIn: 'root'
})
export class AdminCustomerService {

  private readonly apiUrl = `${environment.apiUrl}/admin/customers`;

  constructor(private http: HttpClient) { }

  // ========================= Customer List =========================

  getAllCustomers(
    page: number,
    size: number,
    sort: string = 'userName,asc'
  ): Observable<PageResponse<UserResponse>> {

    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sort', sort);

    return this.http.get<PageResponse<UserResponse>>(this.apiUrl, { params });
  }

  // ========================= Customer Details =========================

  getCustomer(username: string): Observable<UserResponse> {

    return this.http.get<UserResponse>(
      `${this.apiUrl}/${username}`
    );
  }

  // ========================= Search Customer =========================

  searchCustomers(
    searchTerm: string,
    page: number,
    size: number,
    sort: string = 'userName,asc'
  ): Observable<PageResponse<UserResponse>> {

    const params = new HttpParams()
      .set('searchTerm', searchTerm)
      .set('page', page)
      .set('size', size)
      .set('sort', sort);

    return this.http.get<PageResponse<UserResponse>>(
      `${this.apiUrl}/search`,
      { params }
    );
  }

  // ========================= Active Customers =========================

  getActiveCustomers(
    page: number,
    size: number
  ): Observable<PageResponse<UserResponse>> {

    const params = new HttpParams()
      .set('page', page)
      .set('size', size);

    return this.http.get<PageResponse<UserResponse>>(
      `${this.apiUrl}/active`,
      { params }
    );
  }

  // ========================= Disabled Customers =========================

  getDisabledCustomers(
    page: number,
    size: number
  ): Observable<PageResponse<UserResponse>> {

    const params = new HttpParams()
      .set('page', page)
      .set('size', size);

    return this.http.get<PageResponse<UserResponse>>(
      `${this.apiUrl}/disabled`,
      { params }
    );
  }

  // ========================= Enable Customer =========================

  enableCustomer(username: string): Observable<void> {

    return this.http.patch<void>(
      `${this.apiUrl}/${username}/enable`,
      {}
    );
  }

  // ========================= Disable Customer =========================

  disableCustomer(username: string): Observable<void> {

    return this.http.patch<void>(
      `${this.apiUrl}/${username}/disable`,
      {}
    );
  }

  // ========================= Delete Customer =========================

  deleteCustomer(username: string): Observable<void> {

    return this.http.delete<void>(
      `${this.apiUrl}/${username}`
    );
  }

  // ========================= Customer Statistics =========================

  getCustomerStatistics(): Observable<CustomerStatistics> {

    return this.http.get<CustomerStatistics>(
      `${this.apiUrl}/statistics`
    );
  }

}
