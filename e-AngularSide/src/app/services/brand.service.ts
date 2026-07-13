import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BrandRequest, BrandResponse } from '../models/brand.model';

@Injectable({
  providedIn: 'root'
})
export class BrandService {

  private baseUrl = 'http://localhost:8080/api/brands';

  constructor(private http: HttpClient) { }

  // ---------------- GET ALL BRANDS ----------------
  getAll(): Observable<BrandResponse[]> {
    return this.http.get<BrandResponse[]>(this.baseUrl);
  }

  // ---------------- GET BY ID ----------------
  getById(id: number): Observable<BrandResponse> {
    return this.http.get<BrandResponse>(`${this.baseUrl}/${id}`);
  }

  // ---------------- CREATE BRAND ----------------
  create(brand: BrandRequest, logo?: File): Observable<BrandResponse> {
    const formData = new FormData();
    formData.append('brand', new Blob([JSON.stringify(brand)], { type: 'application/json' }));
    if (logo) {
      formData.append('logo', logo);
    }
    return this.http.post<BrandResponse>(this.baseUrl, formData);
  }

  // ---------------- UPDATE BRAND ----------------
  update(id: number, brand: BrandRequest, logo?: File): Observable<BrandResponse> {
    const formData = new FormData();
    formData.append('brand', new Blob([JSON.stringify(brand)], { type: 'application/json' }));
    if (logo) {
      formData.append('logo', logo);
    }
    return this.http.put<BrandResponse>(`${this.baseUrl}/${id}`, formData);
  }

  // ---------------- DELETE BRAND ----------------
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
