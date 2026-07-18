import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../services/environments';
import {
  OrderPage,
  OrderRequest,
  OrderResponse,
  OrderStatus
} from '../models/order.model';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private apiUrl = `${environment.apiUrl}/orders`;

  constructor(private http: HttpClient) { }

  createOrder(request: OrderRequest): Observable<OrderResponse> {
    return this.http.post<OrderResponse>(this.apiUrl, request);
  }

  getMyOrders(
    page: number = 0,
    size: number = 10,
    sort: string = 'id,desc'
  ): Observable<OrderPage> {

    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sort', sort);

    return this.http.get<OrderPage>(this.apiUrl, { params });
  }

  getMyOrdersByStatus(
    status: OrderStatus,
    page: number = 0,
    size: number = 10,
    sort: string = 'id,desc'
  ): Observable<OrderPage> {

    const params = new HttpParams()
      .set('status', status)
      .set('page', page)
      .set('size', size)
      .set('sort', sort);

    return this.http.get<OrderPage>(`${this.apiUrl}/status`, { params });
  }

  getOrder(orderId: number): Observable<OrderResponse> {
    return this.http.get<OrderResponse>(`${this.apiUrl}/${orderId}`);
  }

  cancelOrder(orderId: number): Observable<OrderResponse> {
    return this.http.patch<OrderResponse>(
      `${this.apiUrl}/${orderId}/cancel`,
      {}
    );
  }

  hasPurchasedProduct(productId: number): Observable<boolean> {
    return this.http.get<boolean>(
      `${this.apiUrl}/purchased/${productId}`
    );
  }


  downloadInvoice(orderId: number) {
  return this.http.get(
    `${this.apiUrl}/invoice/${orderId}`,
    {
      responseType: 'blob'
    }
  );
}

}