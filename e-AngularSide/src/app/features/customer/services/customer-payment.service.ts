import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/app/services/environments';
import { PaymentResponse } from 'src/app/shared/models/payment.model';


@Injectable({
  providedIn: 'root'
})
export class CustomerPaymentService {

  private apiUrl = `${environment.apiUrl}/payments`;

  constructor(private http: HttpClient) {}

  createPayment(
    request: PaymentRequest
  ): Observable<PaymentResponse> {

    return this.http.post<PaymentResponse>(
      this.apiUrl,
      request
    );
  }

  getPaymentById(
    paymentId: number
  ): Observable<PaymentResponse> {

    return this.http.get<PaymentResponse>(
      `${this.apiUrl}/${paymentId}`
    );
  }

  getPaymentByOrderId(
    orderId: number
  ): Observable<PaymentResponse> {

    return this.http.get<PaymentResponse>(
      `${this.apiUrl}/order/${orderId}`
    );
  }
}