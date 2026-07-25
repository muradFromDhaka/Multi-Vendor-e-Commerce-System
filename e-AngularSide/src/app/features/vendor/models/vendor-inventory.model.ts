export enum StockStatus {
  IN_STOCK = 'IN_STOCK',
  LOW_STOCK = 'LOW_STOCK',
  OUT_OF_STOCK = 'OUT_OF_STOCK'
}




export interface InventoryListResponse {

  variantId: number;

  productName: string;

  sku: string;

  imageUrl: string;

  attributes: string;

  price: number;

  discountPrice: number | null;

  stock: number;

  stockStatus: StockStatus;

  updatedAt: string;

}


export interface UpdateInventoryRequest {

  stock: number;

}