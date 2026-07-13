import { Injectable } from '@angular/core';
import { environment } from './environments';
import { BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { CartDto, CartItemRequest } from '../models/cart.model';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private apiUrl = `${environment.apiUrl}/cart`;

  /* ===============================
     Global Cart State
     =============================== */
  private cartSubject = new BehaviorSubject<CartDto>({
    items: [],
    totalAmount: 0
  });

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
        this.cartSubject.next({ items: [], totalAmount: 0 });
      }
    });
  }



addItemToCart(request: CartItemRequest): void {
  this.http.post<CartDto>(`${this.apiUrl}/add`, request).subscribe({
    next: cart => this.cartSubject.next(cart),
    error: err => {
      console.error('Error adding item:', err);

      console.log(request);

      console.log("Variant ID:", request.variantId);

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
      .put<CartDto>(`${this.apiUrl}/update/${cartItemId}`, request)
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
      .delete<CartDto>(`${this.apiUrl}/remove/${cartItemId}`)
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
  
