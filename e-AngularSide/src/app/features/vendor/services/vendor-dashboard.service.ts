import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/app/services/environments';
import { VendorDashboard, VendorPerformanceResponse } from '../models/vendor-dashboard.model';

@Injectable({
  providedIn: 'root'
})
export class VendorDashboardService {

  private apiUrl =
    `${environment.apiUrl}/vendor/dashboard`;

  constructor(
    private http: HttpClient
  ) { }

  getDashboard(): Observable<VendorDashboard> {

    return this.http.get<VendorDashboard>(
      this.apiUrl
    );

  }


  getPerformance(
  fromDate: string,
  toDate: string
): Observable<VendorPerformanceResponse> {

  let params = new HttpParams()
    .set('fromDate', fromDate)
    .set('toDate', toDate);

  return this.http.get<VendorPerformanceResponse>(
    `${this.apiUrl}/performance`,
    { params }
  );
}

}