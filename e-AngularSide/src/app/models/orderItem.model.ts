import { AttributeValueResponse } from "../features/admin/models/variants/attributeValue.model";

export interface OrderItemRequest {
  productVariantId: number;
  quantity: number;
}


export interface OrderItemResponse {

  orderId: number;

  vendorOrderId?: number;

  vendorId: number;

  vendorName: string;

  productId: number;

  productName: string;

  variantId: number;
  variantName: string;
  sku: string;

  imageUrl: string | null;

  unitPrice: number;

  quantity: number;

  totalPrice: number;

  attributes: AttributeValueResponse[];
}