import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/app/services/environments';
import { WishlistResponse } from '../models/wishlist.model';


@Injectable({
  providedIn: 'root'
})
export class WishlistService {


  private apiUrl =
    environment.apiUrl + '/wishlist';


  constructor(
    private http: HttpClient
  ) {}


  getWishlist(): Observable<WishlistResponse>{

    return this.http.get<WishlistResponse>(
      this.apiUrl
    );

  }



  addToWishlist(
    productId:number
  ): Observable<WishlistResponse>{

    return this.http.post<WishlistResponse>(
      `${this.apiUrl}/${productId}`,
      {}
    );

  }



  removeFromWishlist(
    productId:number
  ): Observable<WishlistResponse>{

    return this.http.delete<WishlistResponse>(
      `${this.apiUrl}/${productId}`
    );

  }



  existsInWishlist(
    productId:number
  ): Observable<boolean>{

    return this.http.get<boolean>(
      `${this.apiUrl}/exists/${productId}`
    );

  }

}