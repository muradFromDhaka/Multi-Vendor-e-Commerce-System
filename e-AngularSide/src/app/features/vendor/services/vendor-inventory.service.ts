import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/app/services/environments';
import { PageResponse } from 'src/app/models/PageResponse';
import { InventoryListResponse, UpdateInventoryRequest } from '../models/vendor-inventory.model';

@Injectable({
  providedIn: 'root'
})
export class VendorInventoryService {

  private readonly apiUrl = `${environment.apiUrl}/vendor/inventory`;

  constructor(
    private http: HttpClient
  ) { }

  getInventory(
    page: number,
    size: number
  ): Observable<PageResponse<InventoryListResponse>> {

    const params = new HttpParams()
      .set('page', page)
      .set('size', size);

    return this.http.get<PageResponse<InventoryListResponse>>(
      this.apiUrl,
      { params }
    );
  }


  getInventoryDetails(
  variantId: number
): Observable<InventoryListResponse> {

  return this.http.get<InventoryListResponse>(
    `${this.apiUrl}/${variantId}`
  );

}

  getLowStockInventory(
    page: number,
    size: number
  ): Observable<PageResponse<InventoryListResponse>> {

    const params = new HttpParams()
      .set('page', page)
      .set('size', size);

    return this.http.get<PageResponse<InventoryListResponse>>(
      `${this.apiUrl}/low-stock`,
      { params }
    );
  }

  getOutOfStockInventory(
    page: number,
    size: number
  ): Observable<PageResponse<InventoryListResponse>> {

    const params = new HttpParams()
      .set('page', page)
      .set('size', size);

    return this.http.get<PageResponse<InventoryListResponse>>(
      `${this.apiUrl}/out-of-stock`,
      { params }
    );
  }

  updateStock(
    variantId: number,
    request: UpdateInventoryRequest
  ): Observable<InventoryListResponse> {

    return this.http.put<InventoryListResponse>(
      `${this.apiUrl}/${variantId}`,
      request
    );
  }

}