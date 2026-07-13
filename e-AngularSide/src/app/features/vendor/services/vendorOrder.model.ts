
import {OrderStatus,PaymentMethod,PaymentStatus} from "../../../models/order.model";
import { OrderItemResponse } from "../../../models/orderItem.model";
import { PageResponse } from "../../../models/PageResponse";
import { ShippingAddressResponse } from "../../../models/shippingAddress.model";

export interface VendorOrderListResponse {

  id: number;

  orderNumber: string;

  customerName: string;

  totalItems: number;

  orderAmount: number;

  orderStatus: OrderStatus;

  paymentStatus: PaymentStatus;

  createdAt: string;
}

export interface VendorOrderDetailsResponse {

  id: number;

  orderNumber: string;

  customerName: string;

  shippingAddress: ShippingAddressResponse;

  subtotal: number;

  shippingFee: number;

  discount: number;

  totalPrice: number;

  orderStatus: OrderStatus;

  paymentStatus: PaymentStatus;

  paymentMethod: PaymentMethod;

  items: OrderItemResponse[];

  createdAt: string;
}

export interface UpdateVendorOrderStatusRequest {

  orderStatus: OrderStatus;

}

export type VendorOrderPage = PageResponse<VendorOrderListResponse>;