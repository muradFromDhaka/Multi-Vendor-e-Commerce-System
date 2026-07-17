import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/app/services/environments';
import { ProductVariantRequest, ProductVariantResponse } from '../../../models/productVariant.model';
import { Observable } from 'rxjs/internal/Observable';
import { PageResponse } from 'src/app/models/PageResponse';

@Injectable({
  providedIn: 'root'
})
export class VendorProductVariantService {

  
    private readonly apiUrl =
      `${environment.apiUrl}/vendor/product-variants`;
  
    constructor(
      private http: HttpClient
    ) {}
  
    // ================= CREATE =================
  
    create(
      dto: ProductVariantRequest,
      images?: File[]
    ): Observable<ProductVariantResponse> {
  
      const formData = new FormData();
  
      formData.append(
        'variant',
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
  
      return this.http.post<ProductVariantResponse>(
        this.apiUrl,
        formData
      );
    }
  
    // ================= UPDATE =================
  
    update(
      id: number,
      dto: ProductVariantRequest,
      images?: File[]
    ): Observable<ProductVariantResponse> {
  
      const formData = new FormData();
  
      formData.append(
        'variant',
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
  
      return this.http.put<ProductVariantResponse>(
        `${this.apiUrl}/${id}`,
        formData
      );
    }
  
    // ================= GET ALL =================
    
    getAll(
      page = 0,
      size = 10,
      sortBy = 'id',
      sortDir = 'asc'
    ): Observable<PageResponse<ProductVariantResponse>> {
  
      const params = new HttpParams()
        .set('page', page)
        .set('size', size)
        .set('sortBy', sortBy)
        .set('sortDir', sortDir);
  
      return this.http.get<PageResponse<ProductVariantResponse>>(
        this.apiUrl,
        { params }
      );
    }
  
    // ================= GET BY ID =================
  
    getById(
      id: number
    ): Observable<ProductVariantResponse> {
  
      return this.http.get<ProductVariantResponse>(
        `${this.apiUrl}/${id}`
      );
    }
  
    // ================= GET BY PRODUCT =================
  
    getByProduct(
      productId: number,
      page = 0,
      size = 10
    ): Observable<PageResponse<ProductVariantResponse>> {
  
      const params = new HttpParams()
        .set('page', page)
        .set('size', size);
  
      return this.http.get<PageResponse<ProductVariantResponse>>(
        `${this.apiUrl}/product/${productId}`,
        { params }
      );
    }
  
  getVariantBySku(
    sku: string
  ): Observable<ProductVariantResponse> {
  
    return this.http.get<ProductVariantResponse>(
      `${this.apiUrl}/sku/${sku}`
    );
  }
  
    // ================= DELETE =================
  
    delete(
      id: number
    ): Observable<void> {
  
      return this.http.delete<void>(
        `${this.apiUrl}/${id}`
      );
    }
  
}
