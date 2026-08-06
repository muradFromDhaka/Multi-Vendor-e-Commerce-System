import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/app/services/environments';
import { VendorDashboard } from '../models/vendor-dashboard.model';

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

}