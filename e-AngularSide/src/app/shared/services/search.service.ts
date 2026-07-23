import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/app/services/environments';
import { SearchResponse } from '../models/search.model';

@Injectable({
  providedIn: 'root'
})
@Injectable({
  providedIn: 'root'
})
export class SearchService {

    private baseUrl = environment.apiUrl + "/search";

    constructor(private http: HttpClient){}

    search(keyword:string){

        return this.http.get<SearchResponse>(
            this.baseUrl,
            {
                params:{
                    keyword
                }
            }
        );

    }

}
