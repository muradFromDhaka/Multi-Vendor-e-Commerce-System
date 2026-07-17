import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/app/services/environments';
import { PageResponse } from 'src/app/models/PageResponse';
import { VendorCustomerDetailsResponse, VendorCustomerResponse } from '../models/vendor-customer.model';

@Injectable({
  providedIn: 'root'
})
export class VendorCustomerService {

  private readonly apiUrl =
    `${environment.apiUrl}/vendor/customers`;

  constructor(
    private http: HttpClient
  ) {}

  /**
   * Customer List
   */
  getAllCustomers(
    page: number = 0,
    size: number = 10,
  ): Observable<PageResponse<VendorCustomerResponse>> {

    const params = new HttpParams()
      .set('page', page)
      .set('size', size)

    return this.http.get<PageResponse<VendorCustomerResponse>>(
      this.apiUrl,
      { params }
    );
  }

  /**
   * Customer Details
   */
  getCustomerDetails(
    userName: string
  ): Observable<VendorCustomerDetailsResponse> {

    return this.http.get<VendorCustomerDetailsResponse>(
      `${this.apiUrl}/${userName}`
    );
  }

}