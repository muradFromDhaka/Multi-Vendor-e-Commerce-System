import { CustomerVendorOrder } from "../features/customer/models/CustomerVendorOrder";
import { OrderItemResponse } from "./orderItem.model";
import { PageResponse } from "./PageResponse";
import { ShippingAddressRequest, ShippingAddressResponse } from "./shippingAddress.model";

export enum OrderStatus {
  
  PENDING = 'PENDING',
  PROCESSING = 'PROCESSING',
  SHIPPED = 'SHIPPED',
  DELIVERED = 'DELIVERED',
  CANCELLED = 'CANCELLED',
  PARTIALLY_DELIVERED = 'PARTIALLY_DELIVERED',
  PARTIALLY_CANCELLED = 'PARTIALLY_CANCELLED',
  RETURNED = 'RETURNED'
}

export enum PaymentStatus {
  PENDING = 'PENDING',
  PAID = 'PAID',
  FAILED = 'FAILED',
  REFUNDED = 'REFUNDED',
  CANCELLED = 'CANCELLED',
}

export enum PaymentMethod {
  CASH_ON_DELIVERY = 'CASH_ON_DELIVERY',
  MOBILE_BANKING = 'MOBILE_BANKING',
  BANK_TRANSFER = 'BANK_TRANSFER',
  CARD = 'CARD'
  // BKASH = 'BKASH',
  // NAGAD = 'NAGAD',
  
}

export enum PaymentProvider {
  MANUAL = 'MANUAL',
  BKASH = 'BKASH',
  NAGAD = 'NAGAD',
  ROCKET = 'ROCKET',
  SSLCOMMERZ = 'SSLCOMMERZ',
  STRIPE = 'STRIPE',

}


export interface OrderRequest {

  shippingAddressId?: number | undefined;

  shippingAddress?: ShippingAddressRequest;

  paymentMethod: PaymentMethod;

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

  vendorOrders: CustomerVendorOrder[];

  transactionId: string;

  paidAt: string;

  refundedAt: string;

  refundedAmount: string;

  createdAt: string;
}

export type OrderPage = PageResponse<OrderResponse>;