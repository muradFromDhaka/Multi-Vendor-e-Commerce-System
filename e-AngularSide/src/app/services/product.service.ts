import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductDetailsResponse, ProductListResponse, ProductRequest, } from '../models/product.model';
import { PageResponse } from '../models/PageResponse';
import { environment } from './environments';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private baseUrl = `${environment.apiUrl}/products`;

  constructor(private http: HttpClient) {}

  // /** CREATE PRODUCT */
  // createProduct(dto: ProductRequest, images?: File[]): Observable<ProductDetailsResponse> {
  //   const formData = new FormData();
  //   formData.append('product', new Blob([JSON.stringify(dto)], { type: 'application/json' }));

  //   if (images) {
  //     for (const file of images) {
  //       formData.append('images', file);
  //     }
  //   }

  //   return this.http.post<ProductDetailsResponse>(this.baseUrl, formData);
  // }

  // /** UPDATE PRODUCT */
  // updateProduct(id: number, dto: ProductRequest, images?: File[]): Observable<ProductDetailsResponse> {

  //   console.log("ProductService.updateProduct called");

  //   const formData = new FormData();
  //   formData.append('product', new Blob([JSON.stringify(dto)], { type: 'application/json' }));

  //   if (images) {
  //     for (const file of images) {
  //       formData.append('images', file);
  //     }
  //   }

  //   console.log("Sending PUT Request");

  //   return this.http.put<ProductDetailsResponse>(`${this.baseUrl}/${id}`, formData);
  // }


  /** LIST ALL PRODUCTS */
 getAllProducts(
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


  /** GET PRODUCT BY ID */
  getProductById(id: number): Observable<ProductDetailsResponse> {
    return this.http.get<ProductDetailsResponse>(`${this.baseUrl}/${id}`);
  }


  getProductsByCategoryId(
  categoryId: number,
  page = 0,
  size = 10,
  sort = 'id,desc'
  ):Observable<PageResponse<ProductListResponse>>{
  return this.http.get<PageResponse<ProductListResponse>>(
  `${this.baseUrl}/category/${categoryId}`,
  {
    params: {
      page,
      size,
      sort
    }
  }
);
}

searchProducts(
  keyword: string,
  page = 0,
  size = 10,
  sort = 'id,desc'
) {
  return this.http.get<PageResponse<ProductListResponse>>(`${this.baseUrl}/search`,{ params: {
  keyword,
  page,
  size,
  sort
} });
}



getProductsByBrand(
  brandId: number,
  page = 0,
  size = 10,
  sort = 'id,desc'
): Observable<PageResponse<ProductListResponse>> {
  return this.http.get<PageResponse<ProductListResponse>>(
  `${this.baseUrl}/brand/${brandId}`,
  {
    params: {
      page,
      size,
      sort
    }
  }
);
}


getProductsByVendor(
  vendorId: number,
  page = 0,
  size = 10,
  sort = 'id,desc'
): Observable<PageResponse<ProductDetailsResponse>> {

  return this.http.get<PageResponse<ProductDetailsResponse>>(
    `${this.baseUrl}/vendor/${vendorId}`,
    {
      params: {
        page,
        size,
        sort
      }
    }
  );
}



  /** DELETE PRODUCT */
  deleteProduct(id: number): Observable<string> {
  return this.http.delete(`${this.baseUrl}/${id}`, {
    responseType: 'text'
  });
}



  /** POPULAR / LATEST / DISCOUNTED / TRENDING */
getMostPopular(
  page = 0,
  size = 10,
  sort = 'id,desc'
  ): Observable<PageResponse<ProductListResponse>> {
    return this.http.get<PageResponse<ProductListResponse>>(
  `${this.baseUrl}/popular`,
  {
    params: {
      page,
      size,
      sort
    }
  }
);
  }

  getLatest(
   page = 0,
  size = 10,
  sort = 'id,desc'
): Observable<PageResponse<ProductListResponse>> {
    return this.http.get<PageResponse<ProductListResponse>>(
  `${this.baseUrl}/latest`,
  {
    params: {
      page,
      size,
      sort
    }
  }
);
  }


  getTrending(
    limit = 10,
   page = 0,
  size = 10,
  sort = 'id,desc'
): Observable<PageResponse<ProductListResponse>> {
    return this.http.get<PageResponse<ProductListResponse>>(
  `${this.baseUrl}/trending`,
  {
    params: {
      page,
      size,
      sort
    }
  }
);
  }

}
