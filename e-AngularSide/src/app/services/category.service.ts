import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CategoryRequest } from '../models/category.model';
import { CategoryResponse } from '../models/category.model';
import { PageResponse } from '../models/PageResponse';
import { environment } from './environments';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private apiUrl = `${environment.apiUrl}/categories`;

  constructor(private http: HttpClient) {}

  // ---------------- CREATE CATEGORY ----------------
  createCategory(dto: CategoryRequest, image?: File): Observable<CategoryResponse> {
    const formData = new FormData();
    formData.append('category', new Blob([JSON.stringify(dto)], { type: 'application/json' }));
    if (image) {
      formData.append('image', image);
    }
    return this.http.post<CategoryResponse>(this.apiUrl, formData);
  }

  // ---------------- UPDATE CATEGORY ----------------
  updateCategory(id: number, dto: CategoryRequest, image?: File): Observable<CategoryResponse> {
    const formData = new FormData();
    formData.append('category', new Blob([JSON.stringify(dto)], { type: 'application/json' }));
    if (image) {
      formData.append('image', image);
    }
    return this.http.put<CategoryResponse>(`${this.apiUrl}/${id}`, formData);
  }

  // ---------------- DELETE CATEGORY ----------------
  deleteCategory(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // ---------------- GET All CATEGORY ----------------
  getAllCategories(): Observable<PageResponse<CategoryResponse>> {
    return this.http.get<PageResponse<CategoryResponse>>(this.apiUrl);
  }

  // ---------------- GET CATEGORY BY ID ----------------
  getCategoryById(id: number): Observable<CategoryResponse> {
    return this.http.get<CategoryResponse>(`${this.apiUrl}/${id}`);
  }

  // ---------------- GET ALL ROOT CATEGORIES ----------------
  getAllRootCategories(): Observable<PageResponse<CategoryResponse>> {
    return this.http.get<PageResponse<CategoryResponse>>(`${this.apiUrl}/root`);
  }

  // ---------------- GET SUBCATEGORIES ----------------
  getSubCategories(parentId: number): Observable<CategoryResponse[]> {
    return this.http.get<CategoryResponse[]>(`${this.apiUrl}/${parentId}/subcategories`);
  }
}
