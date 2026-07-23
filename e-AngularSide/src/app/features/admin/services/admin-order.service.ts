import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/app/services/environments';
import { AdminOrderDetailsResponse } from '../models/AdminOrder.model';
import { AdminOrderPage } from '../models/AdminOrder.model';
import { OrderStatus, PaymentStatus } from 'src/app/models/order.model';
import { UpdatePaymentStatusRequest } from 'src/app/shared/models/payment.model';



@Injectable({
  providedIn: 'root'
})
export class AdminOrderService {

  private apiUrl = `${environment.apiUrl}/admin/orders`;

  constructor(private http: HttpClient) { }

  // =====================================================
  // Order Management
  // =====================================================

  getAllOrders(
    page: number = 0,
    size: number = 10,
    sort: string = 'createdAt,desc'
  ): Observable<AdminOrderPage> {

    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sort', sort);

    return this.http.get<AdminOrderPage>(
      this.apiUrl,
      { params }
    );
  }

  getOrderDetails(
    orderId: number
  ): Observable<AdminOrderDetailsResponse> {

    return this.http.get<AdminOrderDetailsResponse>(
      `${this.apiUrl}/${orderId}`
    );
  }

updatePaymentStatus(
  orderId: number,
  request: UpdatePaymentStatusRequest
): Observable<AdminOrderDetailsResponse> {

  return this.http.patch<AdminOrderDetailsResponse>(
    `${this.apiUrl}/${orderId}/payment-status`,
    request
  );
}


cancelOrder(
    orderId:number
):Observable<AdminOrderDetailsResponse>{

   return this.http.patch<AdminOrderDetailsResponse>(
      `${this.apiUrl}/${orderId}/cancel`,
      {}
   );
}


  // =====================================================
  // Search
  // =====================================================

  getOrderById(
    orderId: number
  ): Observable<AdminOrderDetailsResponse> {

    return this.http.get<AdminOrderDetailsResponse>(
      `${this.apiUrl}/search/${orderId}`
    );
  }

  getOrderByOrderNumber(
    orderNumber: string
  ): Observable<AdminOrderDetailsResponse> {

    return this.http.get<AdminOrderDetailsResponse>(
      `${this.apiUrl}/search/order-number/${orderNumber}`
    );
  }

  // =====================================================
  // Revenue
  // =====================================================

  getTotalRevenue(): Observable<number> {

    return this.http.get<number>(
      `${this.apiUrl}/revenue/total`
    );
  }

  getTodayRevenue(): Observable<number> {

    return this.http.get<number>(
      `${this.apiUrl}/revenue/today`
    );
  }

  getMonthlyRevenue(): Observable<number> {

    return this.http.get<number>(
      `${this.apiUrl}/revenue/month`
    );
  }

  getYearlyRevenue(): Observable<number> {

    return this.http.get<number>(
      `${this.apiUrl}/revenue/year`
    );
  }

  getRevenueBetween(
    start: string,
    end: string
  ): Observable<number> {

    const params = new HttpParams()
      .set('start', start)
      .set('end', end);

    return this.http.get<number>(
      `${this.apiUrl}/revenue`,
      { params }
    );
  }

  // =====================================================
  // Dashboard Statistics
  // =====================================================

  getTotalOrders(): Observable<number> {

    return this.http.get<number>(
      `${this.apiUrl}/statistics/total-orders`
    );
  }

  getOrderCountByStatus(
    status: OrderStatus
  ): Observable<number> {

    const params = new HttpParams()
      .set('status', status);

    return this.http.get<number>(
      `${this.apiUrl}/statistics/order-status`,
      { params }
    );
  }

  getPaymentCountByStatus(
    status: PaymentStatus
  ): Observable<number> {

    const params = new HttpParams()
      .set('status', status);

    return this.http.get<number>(
      `${this.apiUrl}/statistics/payment-status`,
      { params }
    );
  }

  getOrdersCreatedBetween(
    start: string,
    end: string
  ): Observable<number> {

    const params = new HttpParams()
      .set('start', start)
      .set('end', end);

    return this.http.get<number>(
      `${this.apiUrl}/statistics/orders-between`,
      { params }
    );
  }

}