import {OrderStatus,PaymentMethod,PaymentStatus} from "../../../models/order.model";
import { OrderItemResponse } from "../../../models/orderItem.model";
import { PageResponse } from "../../../models/PageResponse";
import { ShippingAddressResponse } from "../../../models/shippingAddress.model";

export enum VendorOrderStatus {

  PENDING = 'PENDING',

  CONFIRMED = 'CONFIRMED',

  PROCESSING = 'PROCESSING',

  PACKED = 'PACKED',

  SHIPPED = 'SHIPPED',

  DELIVERED = 'DELIVERED',

  CANCELLED = 'CANCELLED',

  RETURNED = 'RETURNED'

}

export interface VendorOrderPaymentInfo{
  paymentStatus: PaymentStatus;
  paymentMethod: PaymentMethod;
  transactionId: string;
  paidAt: number;
}


export interface VendorOrderListResponse {

  id: number;

  orderNumber: string;

  customerName: string;

  totalItems: number;

  vendorOrderAmount: number;

  vendorOrderStatus: VendorOrderStatus;

  paymentStatus: PaymentStatus;

  createdAt: string;
}

export interface VendorOrderDetailsResponse {

  id: number;

  orderNumber: string;

  vendorName: string;

  customerName: string;

  shippingAddress: ShippingAddressResponse;

  subtotal: number;

  shippingFee: number;

  discount: number;

  totalPrice: number;

  vendorOrderStatus: VendorOrderStatus;

  payment: VendorOrderPaymentInfo;

  items: OrderItemResponse[];

  createdAt: string;
}

export interface UpdateVendorOrderStatusRequest {

  vendorOrderStatus: VendorOrderStatus;

}

export type VendorOrderPage = PageResponse<VendorOrderListResponse>;