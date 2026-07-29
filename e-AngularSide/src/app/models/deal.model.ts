import { PageResponse } from "./PageResponse";

export interface DealRequest {

  title: string;

  discountPercent: number;

  startTime: string;

  endTime: string;

  productId: number;

}

export interface DealResponse {

  id: number;

  title: string;

  discountPercent: number;

  active: boolean;

  startTime: string;

  endTime: string;

  productId: number;

  productImage: string;

  productName: string;

}

export type DealPageResponse = PageResponse<DealResponse>;