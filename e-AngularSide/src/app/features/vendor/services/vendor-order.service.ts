import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/app/services/environments';
import { UpdateVendorOrderStatusRequest, VendorOrderDetailsResponse, VendorOrderPage } from '../models/vendorOrder.model';



@Injectable({
  providedIn: 'root'
})
export class VendorOrderService {

  private apiUrl = `${environment.apiUrl}/vendor/orders`;

  constructor(private http: HttpClient) {}

  getVendorOrders(
    page: number = 0,
    size: number = 10,
    sort: string = 'createdAt,desc'
  ): Observable<VendorOrderPage> {

    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sort', sort);

    return this.http.get<VendorOrderPage>(
      this.apiUrl,
      { params }
    );
  }

  getVendorOrderDetails(
    vendorOrderId: number
  ): Observable<VendorOrderDetailsResponse> {

    return this.http.get<VendorOrderDetailsResponse>(
      `${this.apiUrl}/${vendorOrderId}`
    );
  }

  updateVendorOrderStatus(
    vendorOrderId: number,
    request: UpdateVendorOrderStatusRequest
  ): Observable<VendorOrderDetailsResponse> {

    return this.http.patch<VendorOrderDetailsResponse>(
      `${this.apiUrl}/${vendorOrderId}/status`,
      request
    );
  }

  getVendorRevenue(): Observable<number> {

    return this.http.get<number>(
      `${this.apiUrl}/dashboard/revenue`
    );
  }

  getVendorCustomers(): Observable<number> {

    return this.http.get<number>(
      `${this.apiUrl}/dashboard/customers`
    );
  }

  getVendorProductsSold(): Observable<number> {

    return this.http.get<number>(
      `${this.apiUrl}/dashboard/products-sold`
    );
  }

}