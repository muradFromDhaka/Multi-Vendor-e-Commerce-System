import { OrderItemRequest, OrderItemResponse } from "./orderItem.model";
import { PageResponse } from "./PageResponse";
import { ShippingAddressRequest, ShippingAddressResponse } from "./shippingAddress.model";

export enum OrderStatus {
  PAID = 'PAID',
  SHIPPED = 'SHIPPED',
  PENDING = 'PENDING',
  DELIVERED = 'DELIVERED',
  CANCELLED = 'CANCELLED',
  CONFIRMED = 'CONFIRMED',
  PROCESSING = 'PROCESSING',
  RETURNED = 'RETURNED'
}

export enum PaymentStatus {
  PENDING = 'PENDING',
  SUCCESS = 'SUCCESS',
  PAID = 'PAID',
  FAILED = 'FAILED',
  REFUNDED = 'REFUNDED',
  CANCELLED = 'CANCELLED',
}

export enum PaymentMethod {
  CASH_ON_DELIVERY = 'CASH_ON_DELIVERY',
  MOBILE_BANKING = 'MOBILE_BANKING',
  BKASH = 'BKASH',
  NAGAD = 'NAGAD',
  CARD = 'CARD'
}


export interface OrderRequest {

  shippingAddress: ShippingAddressRequest;

  paymentMethod: PaymentMethod;

  items: OrderItemRequest[];
}

export interface OrderResponse {

  id: number;

  orderNumber: string;

  userName: string;

  subtotal: number;

  shippingFee: number;

  discount: number;

  totalPrice: number;

  orderStatus: OrderStatus;

  paymentStatus: PaymentStatus;

  paymentMethod: PaymentMethod;

  shippingAddress: ShippingAddressResponse;

  items: OrderItemResponse[];

  createdAt: string;
}

export type OrderPage = PageResponse<OrderResponse>;