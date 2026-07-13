import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductDetailsResponse, ProductListResponse, ProductRequest, } from '../models/product.model';
import { PageResponse } from '../models/PageResponse';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private baseUrl = 'http://localhost:8080/api/products';

  constructor(private http: HttpClient) {}

  /** CREATE PRODUCT */
  createProduct(dto: ProductRequest, images?: File[]): Observable<ProductDetailsResponse> {
    const formData = new FormData();
    formData.append('product', new Blob([JSON.stringify(dto)], { type: 'application/json' }));

    if (images) {
      for (const file of images) {
        formData.append('images', file);
      }
    }

    return this.http.post<ProductDetailsResponse>(this.baseUrl, formData);
  }

  /** UPDATE PRODUCT */
  updateProduct(id: number, dto: ProductRequest, images?: File[]): Observable<ProductDetailsResponse> {

    console.log("ProductService.updateProduct called");

    const formData = new FormData();
    formData.append('product', new Blob([JSON.stringify(dto)], { type: 'application/json' }));

    if (images) {
      for (const file of images) {
        formData.append('images', file);
      }
    }

    console.log("Sending PUT Request");

    return this.http.put<ProductDetailsResponse>(`${this.baseUrl}/${id}`, formData);
  }

  /** LIST ALL PRODUCTS */
  getAllProducts(): Observable<PageResponse<ProductListResponse>> {
    return this.http.get<PageResponse<ProductListResponse>>(this.baseUrl);
  }


  /** GET PRODUCT BY ID */
  getProductById(id: number): Observable<ProductDetailsResponse> {
    return this.http.get<ProductDetailsResponse>(`${this.baseUrl}/${id}`);
  }


  getProductsByCategoryId(categoryId: number):Observable<PageResponse<ProductListResponse>>{
  return this.http.get<PageResponse<ProductListResponse>>(`${this.baseUrl}/category/${categoryId}`);
}



searchProducts(keyword: string) {
  return this.http.get<PageResponse<ProductListResponse>>(`${this.baseUrl}/search`,{ params: { keyword } });
}



getProductsByBrand(brandId: number): Observable<PageResponse<ProductListResponse>> {
  return this.http.get<PageResponse<ProductListResponse>>(`${this.baseUrl}/brand/${brandId}`);
}


getProductsByVendor(): Observable<PageResponse<ProductListResponse>> {
  return this.http.get<PageResponse<ProductListResponse>>(`${this.baseUrl}/my/product`);
}



  /** DELETE PRODUCT */
  deleteProduct(id: number): Observable<string> {
  return this.http.delete(`${this.baseUrl}/${id}`, {
    responseType: 'text'
  });
}



  /** POPULAR / LATEST / DISCOUNTED / TRENDING */
  getMostPopular(limit = 10): Observable<PageResponse<ProductListResponse>> {
    return this.http.get<PageResponse<ProductListResponse>>(`${this.baseUrl}/popular?limit=${limit}`);
  }

  getLatest(limit = 10): Observable<PageResponse<ProductListResponse>> {
    return this.http.get<PageResponse<ProductListResponse>>(`${this.baseUrl}/latest?limit=${limit}`);
  }


  getTrending(limit = 10): Observable<PageResponse<ProductListResponse>> {
    return this.http.get<PageResponse<ProductListResponse>>(`${this.baseUrl}/trending?limit=${limit}`);
  }


  getMyProducts(): Observable<PageResponse<ProductDetailsResponse>> {
  return this.http.get<PageResponse<ProductDetailsResponse>>(
    `${this.baseUrl}/my/product`
  );
}

}
