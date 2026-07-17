import { CartItemResponse } from "./cart-item.model";

export interface CartDto {

  cartId?: number;

  items: CartItemResponse[];

  totalItems: number;

  subtotal: number;

  shippingFee: number;

  discount: number;

  totalAmount: number;
}


export const EMPTY_CART: CartDto = {

  items: [],

  totalItems: 0,

  subtotal: 0,

  shippingFee: 0,

  discount: 0,

  totalAmount: 0

};

