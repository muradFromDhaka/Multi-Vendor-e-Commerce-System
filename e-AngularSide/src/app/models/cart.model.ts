export interface CartDto {
  cartId?: number;
  items: CartItemResponse[];
  totalAmount: number;
}

// export interface ItemDto {
//   itemId: number;
//   productId: number;
//   productName: string;
//   quantity: number;
//   price: number;
//   total: number;
// }

export interface CartItemRequest {
  variantId: number;
  quantity: number;
}
export interface CartItemResponse{

  itemId: number;

  productId: number;

  variantId: number;

  productName: string;

  sku: string;

  quantity: number;

  unitPrice: number;

  totalPrice: number;

  imageUrl?: string;

  vendorId: number;

  vendorName: string;

}