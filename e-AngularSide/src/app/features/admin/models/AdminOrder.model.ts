import { OrderStatus, PaymentMethod, PaymentStatus } from "src/app/models/order.model";
import { OrderItemResponse } from "src/app/models/orderItem.model";
import { PageResponse } from "src/app/models/PageResponse";
import { ShippingAddressResponse } from "src/app/models/shippingAddress.model";


export interface AdminOrderListResponse {

  id: number;

  orderNumber: string;

  customerName: string;

  totalItems: number;

  totalPrice: number;

  orderStatus: OrderStatus;

  paymentStatus: PaymentStatus;

  createdAt: string;

}

export interface AdminOrderDetailsResponse {

  id: number;

  orderNumber: string;

  customerName: string;

  email: string;

  phone: string;

  subtotal: number;

  shippingFee: number;

  discount: number;

  totalPrice: number;

  orderStatus: OrderStatus;

  paymentStatus: PaymentStatus;

  paymentMethod: PaymentMethod;

  shippingAddress: ShippingAddressResponse;

  items: OrderItemResponse[];

  
  transactionId?: string;

  paidAt?: string;

  refundedAt?: string;

  refundTransactionId?: string;

  refundedAmount?: number;

  createdAt: string;

  updatedAt: string;

}


// export interface  UpdatePaymentStatusRequest {

//          paymentStatus: PaymentStatus;

//          transactionId: string;

//          refundTransactionId: string;

//          refundedAmount: number;

// }

export interface UpdateOrderStatusRequest {

  orderStatus: OrderStatus;
  paymentStatus?: PaymentStatus;
}

export type AdminOrderPage = PageResponse<AdminOrderListResponse>;