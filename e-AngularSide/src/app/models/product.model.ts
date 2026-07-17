import { ProductVariantResponse } from "./productVariant.model";

export enum ProductStatus {
  ACTIVE = 'ACTIVE',
  OUT_OF_STOCK = 'OUT_OF_STOCK',
  DISCONTINUED = 'DISCONTINUED',
  DRAFT = 'DRAFT'
}

export interface ProductRequest {

  name: string;

  description?: string;

  categoryId: number;

  brandId: number;

  vendorId?: number;

  status?: ProductStatus;

  releaseDate?: string;

  imageUrls?: string[];
}


export interface ProductDetailsResponse {

  id: number;

  name: string;

  description?: string;

  status: ProductStatus;

  releaseDate?: string;

  averageRating?: number;

  totalReviews?: number;

  imageUrls: string[];

  totalVariants: number;

  productVariants: ProductVariantResponse[];

  categoryId: number;
  categoryName: string;

  brandId: number;
  brandName: string;

  vendorId: number;
  vendorName: string;

  soldCount: number;
}





export interface ProductListResponse {

  id: number;

  name: string;

  thumbnailUrl?: string;

  price: number;

  discountPrice?: number;

  averageRating?: number;

  totalReviews?: number;

  productVariantId: number;

  categoryId: number;

  categoryName: string;

  brandId: number;

  brandName: string;

  totalVariants: number;

  status: ProductStatus;
}