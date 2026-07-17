import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PageResponse } from 'src/app/models/PageResponse';
import { AttributeValueResponse } from 'src/app/models/variants/attributeValue.model';
import { environment } from 'src/app/services/environments';

@Injectable({
  providedIn: 'root'
})
export class VendorAttributeValueService {

   private apiUrl = `${environment.apiUrl}/vendor/attribute-values`;

  constructor(private http: HttpClient) {}

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

  getAttributeValueById(id: number): Observable<AttributeValueResponse> {
    return this.http.get<AttributeValueResponse>(
      `${this.apiUrl}/${id}`
    );
  }

  getAttributeValuesByAttributeId(
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
}
