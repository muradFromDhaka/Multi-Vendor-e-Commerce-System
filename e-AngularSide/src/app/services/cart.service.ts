import { Injectable } from '@angular/core';
import { environment } from './environments';
import { BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { CartDto, EMPTY_CART } from '../models/cart.model';
import { CartItemRequest } from '../models/cart-item.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private apiUrl = `${environment.apiUrl}/cart`;

  /* ===============================
     Global Cart State
     =============================== */
  private cartSubject = new BehaviorSubject<CartDto>({...EMPTY_CART});

  cart$ = this.cartSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) { }

  /* ===============================
     Load Cart (call once)
     =============================== */
  loadCart(): void {
    this.http.get<CartDto>(this.apiUrl).subscribe({
      next: cart => this.cartSubject.next(cart),
      error: err => {
        console.error('Error loading cart:', err);
        this.cartSubject.next({...EMPTY_CART});
      }
    });
  }



addItemToCart(request: CartItemRequest): void {
  this.http.post<CartDto>(`${this.apiUrl}`, request).subscribe({
    next: cart => this.cartSubject.next(cart),
    error: err => {
      console.error('Error adding item:', err);

      console.log(request);

      console.log("Variant ID:", request.productVariantId);

      // 401 Unauthorized check
      if (err.status === 401) {
        alert('Please login irst!');
        this.router.navigate(['/login']); // Redirect to login page
      } else {
        alert('Something went wrong, please try again.');
      }
    }
  });
}

  /* ===============================
     Update Item
     =============================== */
  updateCartItem(cartItemId: number, request: CartItemRequest): void {
    this.http
      .put<CartDto>(`${this.apiUrl}/${cartItemId}`, request)
      .subscribe({
        next: cart => this.cartSubject.next(cart),
        error: err => console.error('Error updating item:', err)
      });
  }

  /* ===============================
     Remove Item
     =============================== */
  removeCartItem(cartItemId: number): void {
    this.http
      .delete<CartDto>(`${this.apiUrl}/${cartItemId}`)
      .subscribe({
        next: cart => {this.loadCart(); },
        error: err => console.error('Error removing item:', err)
      });
  }

  /* ===============================
     Clear Cart
     =============================== */
  clearCart(): void {
    this.http
      .delete<CartDto>(`${this.apiUrl}/clear`)
      .subscribe({
        next: cart => this.cartSubject.next(cart),
        error: err => console.error('Error clearing cart:', err)
      });
  }

  /* ===============================
     Checkout
     =============================== */
  // checkout(): void {
  //   this.http
  //     .post<CartDto>(`${this.apiUrl}/checkout`, {})
  //     .subscribe({
  //       next: cart => this.cartSubject.next(cart),
  //       error: err => console.error('Checkout failed:', err)
  //     });
  // }

  /* ===============================
     Helpers
     =============================== */
  getCartSnapshot(): CartDto {
    return this.cartSubject.value;
  }

  
  getTotalItems(): number {
    return this.cartSubject.value.items.reduce(
      (sum, item) => sum + item.quantity,
      0
    );
  }
}
  
