import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import {
  VendorRequest,
  VendorResponse,
  VendorStats,
  VendorStatus,
  VendorSummary
} from 'src/app/models/vendor.model';
import { OrderResponse } from 'src/app/models/order.model';
import { PageResponse } from 'src/app/models/PageResponse';
import { ProductDetailsResponse, ProductListResponse } from 'src/app/models/product.model';

@Injectable({
  providedIn: 'root'
})
export class AdminVendorService {

  private readonly baseUrl = 'http://localhost:8080/api/admin/vendors';

  constructor(private http: HttpClient) {}


  getVendors(status?: string, search?: string): Observable<VendorResponse[]> {

  let url = this.baseUrl;

  const params: string[] = [];

  if (status) {
    params.push(`status=${status}`);
  }

  if (search) {
    params.push(`search=${search}`);
  }

  if (params.length > 0) {
    url += '?' + params.join('&');
  }

  return this.http.get<VendorResponse[]>(url);
}



  getVendorById(id: number): Observable<VendorResponse> {
    return this.http.get<VendorResponse>(
      `${this.baseUrl}/${id}`
    );
  }


  updateVendor(
  id: number,
  vendor: VendorRequest,
  logo?: File,
  banner?: File
): Observable<VendorResponse> {

  const formData = new FormData();

  formData.append(
    'vendor',
    new Blob([JSON.stringify(vendor)], {
      type: 'application/json'
    })
  );

  if (logo) {
    formData.append('logo', logo);
  }

  if (banner) {
    formData.append('banner', banner);
  }

  return this.http.put<VendorResponse>(
    `${this.baseUrl}/${id}`,
    formData
  );
}


  updateVendorStatus(
    id: number,
    status: VendorStatus
  ): Observable<VendorResponse> {
  return this.http.put<VendorResponse>(
    `${this.baseUrl}/${id}/status?status=${status}`,
    {}
  );
}

  // Shortcut Methods
  approveVendor(id: number): Observable<VendorResponse> {
    return this.updateVendorStatus(id, VendorStatus.APPROVED);
  }

  rejectVendor(id: number): Observable<VendorResponse> {
    return this.updateVendorStatus(id, VendorStatus.REJECTED);
  }

  suspendVendor(id: number): Observable<VendorResponse> {
    return this.updateVendorStatus(id, VendorStatus.SUSPENDED);
  }

  activateVendor(id: number): Observable<VendorResponse> {
    return this.updateVendorStatus(id, VendorStatus.ACTIVE);
  }


  getVendorSummary() {
    
    return this.http.get<VendorSummary>(
        `${this.baseUrl}/summary`
    );
}



getVendorProducts(
  vendorId: number,
  page: number = 0,
  size: number = 10
): Observable<PageResponse<ProductDetailsResponse>> {

  return this.http.get<PageResponse<ProductDetailsResponse>>(
    `${this.baseUrl}/${vendorId}/products?page=${page}&size=${size}`
  );

}

getVendorOrders(
  vendorId: number,
  page: number = 0,
  size: number = 10
): Observable<PageResponse<OrderResponse>> {

  const params = new HttpParams()
    .set('page', page)
    .set('size', size);

  return this.http.get<PageResponse<OrderResponse>>(
    `${this.baseUrl}/${vendorId}/orders`,
    { params }
  );
}


getVendorStats(vendorId: number): Observable<VendorStats> {

  return this.http.get<VendorStats>(
    `${this.baseUrl}/${vendorId}/stats`
  );

}


  deleteVendor(id: number): Observable<void> {
    return this.http.delete<void>(
      `${this.baseUrl}/${id}`
    );
  }

}