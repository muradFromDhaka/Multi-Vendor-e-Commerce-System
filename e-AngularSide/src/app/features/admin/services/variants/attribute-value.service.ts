import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/app/services/environments';
import { PageResponse } from 'src/app/models/PageResponse';
import { AttributeValueRequest, AttributeValueResponse } from '../../models/variants/attributeValue.model';

@Injectable({
  providedIn: 'root'
})
export class AttributeValueService {

  private apiUrl = `${environment.apiUrl}/admin/attribute-values`;

  constructor(private http: HttpClient) {}

  create(
    dto: AttributeValueRequest
  ): Observable<AttributeValueResponse> {

    return this.http.post<AttributeValueResponse>(
      this.apiUrl,
      dto
    );
  }

  getAll(
    page = 0,
    size = 10,
    sortBy = 'id',
    sortDir = 'asc'
  ): Observable<PageResponse<AttributeValueResponse>> {

    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sortBy', sortBy)
      .set('sortDir', sortDir);

    return this.http.get<PageResponse<AttributeValueResponse>>(
      this.apiUrl,
      { params }
    );
  }

  getById(id: number): Observable<AttributeValueResponse> {
    return this.http.get<AttributeValueResponse>(
      `${this.apiUrl}/${id}`
    );
  }

  getByAttribute(
    attributeId: number,
    page = 0,
    size = 10
  ): Observable<PageResponse<AttributeValueResponse>> {

    const params = new HttpParams()
      .set('page', page)
      .set('size', size);

    return this.http.get<PageResponse<AttributeValueResponse>>(
      `${this.apiUrl}/attribute/${attributeId}`,
      { params }
    );
  }

  update(
    id: number,
    dto: AttributeValueRequest
  ): Observable<AttributeValueResponse> {

    return this.http.put<AttributeValueResponse>(
      `${this.apiUrl}/${id}`,
      dto
    );
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}