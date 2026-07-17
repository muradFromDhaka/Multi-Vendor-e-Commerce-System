import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/app/services/environments';
import { CartDto } from 'src/app/models/cart.model';
import { OrderRequest, OrderResponse } from 'src/app/models/order.model';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

  private readonly apiUrl = `${environment.apiUrl}/checkout`;

  constructor(private http: HttpClient) {}

  /**
   * Checkout Summary
   */
  getCheckoutSummary(): Observable<CartDto> {
    return this.http.get<CartDto>(
      `${this.apiUrl}/summary`
    );
  }

  /**
   * Place Order
   */
  placeOrder(request: OrderRequest): Observable<OrderResponse> {
    return this.http.post<OrderResponse>(
      `${this.apiUrl}/place-order`,
      request
    );
  }

}