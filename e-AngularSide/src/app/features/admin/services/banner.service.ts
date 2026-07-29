import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/app/services/environments';
import { BannerResponse } from '../models/banner.model';
import { PageResponse } from 'src/app/models/PageResponse';

@Injectable({
  providedIn: 'root'
})
export class BannerService {


  private readonly apiUrl = `${environment.apiUrl}/admin/banners`;


  constructor(
    private http: HttpClient
  ) {}



  // ==========================
  // Create Banner
  // ==========================

  createBanner(
    formData: FormData
  ): Observable<BannerResponse> {


    return this.http.post<BannerResponse>(
      this.apiUrl,
      formData
    );

  }





  // ==========================
  // Update Banner
  // ==========================

  updateBanner(
    bannerId:number,
    formData:FormData
  ):Observable<BannerResponse>{


    return this.http.put<BannerResponse>(
      `${this.apiUrl}/${bannerId}`,
      formData
    );

  }





  // ==========================
  // Delete Banner
  // ==========================

  deleteBanner(
    bannerId:number
  ):Observable<string>{


    return this.http.delete(
      `${this.apiUrl}/${bannerId}`,
      {
        responseType:'text'
      }
    );

  }





  // ==========================
  // Get Single Banner
  // ==========================

  getBanner(
    bannerId:number
  ):Observable<BannerResponse>{


    return this.http.get<BannerResponse>(
      `${this.apiUrl}/${bannerId}`
    );

  }





  // ==========================
  // Get All Banners (Admin)
  // ==========================

  getAllBanners()
  :Observable<BannerResponse[]>{


    return this.http.get<BannerResponse[]>(
      this.apiUrl
    );

  }





  // ==========================
  // Get Active Banners (Public)
  // ==========================

  getActiveBanners(
    page:number = 0,
    size:number = 5
  ):Observable<PageResponse<BannerResponse>>{


    const params = new HttpParams()
      .set('page',page)
      .set('size',size);



    return this.http.get<PageResponse<BannerResponse>>(
      `${this.apiUrl}/active`,
      {
        params
      }
    );


  }



}