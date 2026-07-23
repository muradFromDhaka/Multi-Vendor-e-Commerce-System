import { PaymentMethod, PaymentProvider, PaymentStatus } from "src/app/models/order.model";

export interface PaymentRequest {

  orderId: number;

  provider: PaymentProvider;

  transactionId?: string;

  paymentMethod: PaymentMethod;
}


export interface UpdatePaymentStatusRequest {

  paymentStatus: PaymentStatus;

  transactionId?: string;

  refundTransactionId?: string;

  refundedAmount?: number;
}


export interface PaymentResponse {

  orderId: number;

  paymentId: number;

  amount: number;

  paidAt: string | null;

  transactionId: string | null;

  provider: PaymentProvider;

  paymentMethod: PaymentMethod;

  paymentStatus: PaymentStatus;

  refundTransactionId: string | null;
 
  refundedAt: string | null;
 
  refundedAmount: number | null;

}