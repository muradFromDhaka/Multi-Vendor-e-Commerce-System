import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/app/services/environments';
import { AdminDashboard } from '../models/dashboard.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminDashboardService {

    private api = `${environment.apiUrl}/admin/dashboard`;
  
    constructor(private http: HttpClient) {}
  
    getDashboard(): Observable<AdminDashboard> {
      return this.http.get<AdminDashboard>(this.api);
    }
}
