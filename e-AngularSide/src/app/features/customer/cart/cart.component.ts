import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CartItemResponse } from 'src/app/models/cart-item.model';
import { CartDto } from 'src/app/models/cart.model';
import { CartService } from 'src/app/services/cart.service';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent {

  cart!: CartDto;

  constructor(
    private cartService: CartService,
    // private orderService:OrderService,
    // private router: Router
  ) {}

  ngOnInit(): void {
    this.cartService.cart$.subscribe(cart => {
      this.cart = cart;
    });
  }

  increaseQty(item: CartItemResponse): void {
    this.cartService.updateCartItem(item.cartItemId, {
      productVariantId: item.productVariantId,
      quantity: item.quantity + 1
    });
  }

  decreaseQty(item: CartItemResponse): void {
    if (item.quantity === 1) return;

    this.cartService.updateCartItem(item.cartItemId, {
      productVariantId: item.productVariantId,
      quantity: item.quantity - 1
    });
  }

  removeItem(item: CartItemResponse): void {
    if (!confirm('Remove this item from cart?')) return;

    this.cartService.removeCartItem(item.cartItemId);
  }

  clearCart(): void {
    if (!confirm('Clear entire cart?')) return;

    this.cartService.clearCart();
  }

  get totalItems(): number {
    return this.cart.items.reduce(
      (sum, i) => sum + i.quantity,
      0
    );
  }




  // checkout() {
  //   const paymentMethod = 'CASH_ON_DELIVERY'; // Or dynamic selection
  //   this.orderService.checkout(paymentMethod).subscribe({
  //     next: order => {
  //       alert('Order placed successfully!');
  //       this.router.navigate(['/orderList']);
  //     },
  //     error: err => {
  //       if (err.status === 401) {
  //         alert('Please login first!');
  //         this.router.navigate(['/login']);
  //       } else {
  //         alert('Something went wrong, please try again.');
  //       }
  //     }
  //   });
  // }



  isPlacingOrder = false;

checkout() {
  if (this.isPlacingOrder) return;

  this.isPlacingOrder = true;

  const paymentMethod = 'CASH_ON_DELIVERY';

  // this.orderService.checkout(paymentMethod).subscribe({
  //   next: order => {
  //     alert('Order placed successfully!');
  //     this.cartService.loadCart(); // refresh cart
  //     this.router.navigate(['/orderView', order.id]); // go to order detail
  //   },
  //   error: err => {
  //     this.isPlacingOrder = false;

  //     if (err.status === 401) {
  //       alert('Please login first!');
  //       this.router.navigate(['/login']);
  //     } else {
  //       alert('Something went wrong');
  //     }
  //   }
  // });
}









}
