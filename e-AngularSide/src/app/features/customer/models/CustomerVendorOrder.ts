import { OrderItemResponse } from "src/app/models/orderItem.model";
import { VendorOrderStatus } from "../../vendor/models/vendorOrder.model";

export interface CustomerVendorOrder {

  vendorOrderId: number;

  vendorId: number;

  vendorName: string;

  vendorOrderStatus: VendorOrderStatus;

  items: OrderItemResponse[];

  updatedAt: string;

}