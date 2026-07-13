import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { VendorRequest, VendorResponse } from 'src/app/models/vendor.model';
import { OrderResponse } from 'src/app/models/order.model';
import { OrderItemResponse } from 'src/app/models/orderItem.model';
import { environment } from 'src/app/services/environments';

@Injectable({
  providedIn: 'root',
})
export class VendorService {
  private baseUrl = `${environment.apiUrl}/vendors`;

  constructor(private http: HttpClient) {}

  // Create Vendor
  createVendor(
  dto: VendorRequest,
  logo?: File,
  banner?: File
): Observable<VendorResponse> {

  const formData = new FormData();

  formData.append(
    'vendor',
    new Blob([JSON.stringify(dto)], {
      type: 'application/json'
    })
  );

  if (logo) {
    formData.append('logo', logo);
  }

  if (banner) {
    formData.append('banner', banner);
  }

  return this.http.post<VendorResponse>(
    this.baseUrl,
    formData
  );
}

  // Update vendor
updateVendor(
  id: number,
  dto: VendorRequest,
  logo?: File,
  banner?: File
): Observable<VendorResponse> {

  const formData = new FormData();

  formData.append(
    'vendor',
    new Blob([JSON.stringify(dto)], {
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


  // Get all vendors (Admin)
  getAllVendors(): Observable<VendorResponse[]> {
    return this.http.get<VendorResponse[]>(this.baseUrl);
  }

  // Get vendor by ID
  getVendorById(id: number): Observable<VendorResponse> {
    return this.http.get<VendorResponse>(`${this.baseUrl}/${id}`);
  }

  // Delete vendor
  deleteVendor(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  // Get logged-in user's vendor
  getMyVendor(): Observable<VendorResponse> {
    return this.http.get<VendorResponse>(`${this.baseUrl}/me`);
  }

  // Logged-in vendor's order items
  myOrderItems(): Observable<OrderItemResponse[]> {
    return this.http.get<OrderItemResponse[]>(`${this.baseUrl}/me/order-items`);
  }

  // Logged-in vendor's orders
  myOrders(): Observable<OrderResponse[]> {
    return this.http.get<OrderResponse[]>(`${this.baseUrl}/me/orders`);
  }
}
