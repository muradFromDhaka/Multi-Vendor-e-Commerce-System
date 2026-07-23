import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PageResponse } from 'src/app/models/PageResponse';
import { PaymentMethod, PaymentStatus } from 'src/app/models/order.model';
import { PaymentResponse, UpdatePaymentStatusRequest } from 'src/app/shared/models/payment.model';
import { environment } from 'src/app/services/environments';



@Injectable({
  providedIn: 'root',
})
export class AdminPaymentService {

  private apiUrl = `${environment.apiUrl}/admin/payments`;

  constructor(private http: HttpClient) {}

  // =====================================================
  // Update Payment Status
  // =====================================================

  updatePaymentStatus(
    paymentId: number,
    request: UpdatePaymentStatusRequest
  ): Observable<PaymentResponse> {

    return this.http.put<PaymentResponse>(
      `${this.apiUrl}/${paymentId}/status`,
      request
    );
  }

  // =====================================================
  // Get Payment By Id
  // =====================================================

  getPaymentById(
    paymentId: number
  ): Observable<PaymentResponse> {

    return this.http.get<PaymentResponse>(
      `${this.apiUrl}/${paymentId}`
    );
  }

  // =====================================================
  // Get Payment By Order Id
  // =====================================================

  getPaymentByOrderId(
    orderId: number
  ): Observable<PaymentResponse> {

    return this.http.get<PaymentResponse>(
      `${this.apiUrl}/order/${orderId}`
    );
  }

  // =====================================================
  // Get Payment By Order Number
  // =====================================================

  getPaymentByOrderNumber(
    orderNumber: string
  ): Observable<PaymentResponse> {

    return this.http.get<PaymentResponse>(
      `${this.apiUrl}/order-number/${orderNumber}`
    );
  }

  // =====================================================
  // Get Payment By Transaction Id
  // =====================================================

  getPaymentByTransactionId(
    transactionId: string
  ): Observable<PaymentResponse> {

    return this.http.get<PaymentResponse>(
      `${this.apiUrl}/transaction/${transactionId}`
    );
  }

  // =====================================================
  // Get Payment By Refund Transaction Id
  // =====================================================

  getPaymentByRefundTransactionId(
    refundTransactionId: string
  ): Observable<PaymentResponse> {

    return this.http.get<PaymentResponse>(
      `${this.apiUrl}/refund/${refundTransactionId}`
    );
  }

  // =====================================================
  // Get All Payments
  // =====================================================

  getAllPayments(
    page = 0,
    size = 10,
    sort = 'createdAt,desc'
  ): Observable<PageResponse<PaymentResponse>> {

    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sort', sort);

    return this.http.get<PageResponse<PaymentResponse>>(
      this.apiUrl,
      { params }
    );
  }

  // =====================================================
  // Get Payments By Status
  // =====================================================

  getPaymentsByStatus(
    status: PaymentStatus,
    page = 0,
    size = 10,
    sort = 'createdAt,desc'
  ): Observable<PageResponse<PaymentResponse>> {

    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sort', sort);

    return this.http.get<PageResponse<PaymentResponse>>(
      `${this.apiUrl}/status/${status}`,
      { params }
    );
  }

  // =====================================================
  // Get Payments By Method
  // =====================================================

  getPaymentsByMethod(
    method: PaymentMethod,
    page = 0,
    size = 10,
    sort = 'createdAt,desc'
  ): Observable<PageResponse<PaymentResponse>> {

    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sort', sort);

    return this.http.get<PageResponse<PaymentResponse>>(
      `${this.apiUrl}/method/${method}`,
      { params }
    );
  }

  // =====================================================
  // Filter By Status & Method
  // =====================================================

  filterPayments(
    paymentStatus: PaymentStatus,
    paymentMethod: PaymentMethod,
    page = 0,
    size = 10,
    sort = 'createdAt,desc'
  ): Observable<PageResponse<PaymentResponse>> {

    const params = new HttpParams()
      .set('paymentStatus', paymentStatus)
      .set('paymentMethod', paymentMethod)
      .set('page', page)
      .set('size', size)
      .set('sort', sort);

    return this.http.get<PageResponse<PaymentResponse>>(
      `${this.apiUrl}/filter`,
      { params }
    );
  }

  // =====================================================
  // Search Customer Payments
  // =====================================================

  searchCustomerPayments(
    username: string,
    page = 0,
    size = 10,
    sort = 'createdAt,desc'
  ): Observable<PageResponse<PaymentResponse>> {

    const params = new HttpParams()
      .set('username', username)
      .set('page', page)
      .set('size', size)
      .set('sort', sort);

    return this.http.get<PageResponse<PaymentResponse>>(
      `${this.apiUrl}/search`,
      { params }
    );
  }

}