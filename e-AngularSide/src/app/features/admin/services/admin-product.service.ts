import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductDetailsResponse, ProductListResponse, ProductRequest, ProductStatus } from 'src/app/models/product.model';
import { PageResponse } from 'src/app/models/PageResponse';
import { environment } from 'src/app/services/environments';

@Injectable({
  providedIn: 'root'
})
export class AdminProductService {

  private readonly baseUrl = `${environment.apiUrl}/admin/products`;

  constructor(private http: HttpClient) {}

  createProduct(
    dto: ProductRequest,
    images?: File[]
  ): Observable<ProductDetailsResponse> {

    const formData = new FormData();

    formData.append(
      'product',
      new Blob([JSON.stringify(dto)], {
        type: 'application/json'
      })
    );

    if (images) {
      images.forEach(image => formData.append('images', image));
    }

    return this.http.post<ProductDetailsResponse>(
      this.baseUrl,
      formData
    );
  }

  updateProduct(
    productId: number,
    dto: ProductRequest,
    images?: File[]
  ): Observable<ProductDetailsResponse> {

    const formData = new FormData();

    formData.append(
      'product',
      new Blob([JSON.stringify(dto)], {
        type: 'application/json'
      })
    );

    if (images) {
      images.forEach(image => formData.append('images', image));
    }

    return this.http.put<ProductDetailsResponse>(
      `${this.baseUrl}/${productId}`,
      formData
    );
  }

  getAllProducts(page = 0, size = 10):Observable<PageResponse<ProductListResponse>> {
  return this.http.get<PageResponse<ProductListResponse>>(
    `${this.baseUrl}?page=${page}&size=${size}&sort=id,desc`
  );
}

  getProductById(
    productId: number
  ): Observable<ProductDetailsResponse> {

    return this.http.get<ProductDetailsResponse>(
      `${this.baseUrl}/${productId}`
    );
  }

  deleteProduct(productId: number): Observable<string> {

    return this.http.delete(
      `${this.baseUrl}/${productId}`,
      {
        responseType: 'text'
      }
    );
  }

  
  changeProductStatus(
    productId: number,
    status: ProductStatus
  ): Observable<ProductDetailsResponse> {

    return this.http.patch<ProductDetailsResponse>(
      `${this.baseUrl}/${productId}/status`,
      { status }
    );
  }

}