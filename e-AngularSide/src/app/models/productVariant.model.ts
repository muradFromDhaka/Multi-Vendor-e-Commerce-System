import { AttributeValueResponse } from "./variants/attributeValue.model";

export interface ProductVariantRequest {

  sku: string;

  price: number;

  discountPrice?: number;

  stock: number;

  productId: number;

  imageUrls?: string[];

  attributeValueIds: number[];
}


export interface ProductVariantResponse {

  id: number;

  sku: string;

  price: number;

  discountPrice: number;

  stock: number;

  productId: number;

  productName: string;

  imageUrls: string[];

  attributeValues: AttributeValueResponse[];
  
}