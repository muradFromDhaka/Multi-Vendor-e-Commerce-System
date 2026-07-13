import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  ProductDetailsResponse,
  ProductRequest
} from 'src/app/models/product.model';
import { PageResponse } from 'src/app/models/PageResponse';
import { environment } from 'src/app/services/environments';

@Injectable({
  providedIn: 'root'
})
export class VendorProductService {

  private readonly baseUrl = `${environment.apiUrl}/vendor/products`;

  constructor(
    private http: HttpClient
  ) {}

 
  createProduct(
    dto: ProductRequest,
    images?: File[]
  ): Observable<ProductDetailsResponse> {

    const formData = new FormData();

    formData.append(
      'product',
      new Blob(
        [JSON.stringify(dto)],
        { type: 'application/json' }
      )
    );

    if (images) {
      images.forEach(file =>
        formData.append('images', file)
      );
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
      new Blob(
        [JSON.stringify(dto)],
        { type: 'application/json' }
      )
    );

    if (images) {
      images.forEach(file =>
        formData.append('images', file)
      );
    }

    return this.http.put<ProductDetailsResponse>(
      `${this.baseUrl}/${productId}`,
      formData
    );
  }

  
  getMyProducts(
    page = 0,
    size = 10,
    sort = 'id,desc'
  ): Observable<PageResponse<ProductDetailsResponse>> {

    return this.http.get<PageResponse<ProductDetailsResponse>>(
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

  getProductById(
    productId: number
  ): Observable<ProductDetailsResponse> {

    return this.http.get<ProductDetailsResponse>(
      `${this.baseUrl}/${productId}`
    );
  }

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

}