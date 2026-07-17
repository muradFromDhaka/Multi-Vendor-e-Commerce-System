export interface VendorCustomerResponse {

  userName: string;

  fullName: string;

  email: string;

  phone: string;

  totalOrders: number;

  totalSpent: number;

  lastOrderDate: string;

}


export interface VendorCustomerOrderItemResponse {

  productId: number;

  productName: string;

  variantId: number;

  sku: string;

  quantity: number;

  unitPrice: number;

  totalPrice: number;

}


export interface VendorCustomerOrderResponse {

  orderId: number;

  orderNumber: string;

  orderStatus: string;

  paymentStatus: string;

  paymentMethod: string;

  vendorTotalPrice: number;

  orderDate: string;

  items: VendorCustomerOrderItemResponse[];

}


export interface VendorCustomerDetailsResponse {

  userName: string;

  fullName: string;

  email: string;

  phone: string;

  totalOrders: number;

  totalSpent: number;

  lastOrderDate: string;

  orders: VendorCustomerOrderResponse[];

}