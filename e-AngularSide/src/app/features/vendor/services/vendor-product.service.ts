import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  ProductDetailsResponse,
  ProductListResponse,
  ProductRequest
} from 'src/app/models/product.model';
import { PageResponse } from 'src/app/models/PageResponse';
import { environment } from 'src/app/services/environments';

@Injectable({
  providedIn: 'root'
})
export class VendorProductService {


  private readonly baseUrl =
    `${environment.apiUrl}/vendor/products`;

  constructor(
    private http: HttpClient
  ) { }

  // ======================================
  // Create Product
  // ======================================

  createProduct(
    dto: ProductRequest,
    images: File[] = []
  ): Observable<ProductDetailsResponse> {

    const formData = this.buildFormData(
      dto,
      images
    );

    return this.http.post<ProductDetailsResponse>(
      this.baseUrl,
      formData
    );

  }

  // ======================================
  // Update Product
  // ======================================

  updateProduct(
    productId: number,
    dto: ProductRequest,
    images: File[] = []
  ): Observable<ProductDetailsResponse> {

    const formData = this.buildFormData(
      dto,
      images
    );

    return this.http.put<ProductDetailsResponse>(
      `${this.baseUrl}/${productId}`,
      formData
    );

  }

  // ======================================
  // Product Details
  // ======================================

  getProductById(
    productId: number
  ): Observable<ProductDetailsResponse> {

    return this.http.get<ProductDetailsResponse>(
      `${this.baseUrl}/${productId}`
    );

  }

  // ======================================
  // My Products
  // ======================================

  getMyProducts(
    page = 0,
    size = 10,
    sort = 'id,desc'
  ): Observable<PageResponse<ProductListResponse>> {

    return this.http.get<PageResponse<ProductListResponse>>(
      this.baseUrl,
      {
        params: {
          page,
          size,
          sort
        }
      }
    );

  }

  // ======================================
  // Delete Product
  // ======================================

  deleteProduct(
    productId: number
  ): Observable<string> {

    return this.http.delete(
      `${this.baseUrl}/${productId}`,
      {
        responseType: 'text'
      }
    );

  }

  // ======================================
  // Common FormData Builder
  // ======================================

  private buildFormData(
    dto: ProductRequest,
    images: File[]
  ): FormData {

    const formData = new FormData();

    formData.append(
      'product',
      new Blob(
        [
          JSON.stringify(dto)
        ],
        {
          type: 'application/json'
        }
      )
    );

    images.forEach(file => {

      formData.append(
        'images',
        file
      );

    });

    return formData;

  }


  // ======================================
// Search My Products
// ======================================

searchProducts(
  keyword: string,
  page = 0,
  size = 10,
  sort = 'id,desc'
): Observable<PageResponse<ProductListResponse>> {

  return this.http.get<PageResponse<ProductListResponse>>(
    `${this.baseUrl}/search`,
    {
      params: {
        keyword,
        page,
        size,
        sort
      }
    }
  );

}

}