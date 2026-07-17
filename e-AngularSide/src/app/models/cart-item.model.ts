
export interface CartItemRequest {
  productVariantId: number;
  quantity: number;
}


export interface CartItemResponse{

  cartItemId: number;

  productId: number;

  productVariantId: number;

  productName: string;

  sku: string;

  quantity: number;

  unitPrice: number;

  totalPrice: number;

  imageUrl?: string;

  vendorId: number;

  vendorName: string;

}