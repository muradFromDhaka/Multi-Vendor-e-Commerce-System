import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PageResponse } from 'src/app/models/PageResponse';
import { AttributeRequest, AttributeResponse } from 'src/app/models/variants/attribute.model';
import { environment } from 'src/app/services/environments';

@Injectable({
  providedIn: 'root'
})
export class VendorAttributeService {

  
  private apiUrl = `${environment.apiUrl}/vendor/attributes`;

  constructor(private http: HttpClient) {}

  create(dto: AttributeRequest): Observable<AttributeResponse> {
    return this.http.post<AttributeResponse>(this.apiUrl, dto);
  }

  getAll(
    page = 0,
    size = 10,
    sortBy = 'id',
    sortDir = 'asc'
  ): Observable<PageResponse<AttributeResponse>> {

    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sortBy', sortBy)
      .set('sortDir', sortDir);

    return this.http.get<PageResponse<AttributeResponse>>(this.apiUrl, {
      params
    });
  }

getAttributeByCategory(
  categoryId: number,
  page = 0,
  size = 10,
  sortBy = 'id',
  sortDir = 'asc'
): Observable<PageResponse<AttributeResponse>> {

  const params = new HttpParams()
    .set('page', page)
    .set('size', size)
    .set('sortBy', sortBy)
    .set('sortDir', sortDir);

  return this.http.get<PageResponse<AttributeResponse>>(
    `${this.apiUrl}/category/${categoryId}`,
    { params }
  );
}


  getById(id: number): Observable<AttributeResponse> {
    return this.http.get<AttributeResponse>(`${this.apiUrl}/${id}`);
  }
}
