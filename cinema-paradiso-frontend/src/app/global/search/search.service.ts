import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {log} from 'util';

const httpOptions = {headers: new HttpHeaders({ 'Content-Type': 'application/json' })};
@Injectable()
export class SearchService{
  constructor(private http: HttpClient) { }

  searchMovies(keyword, page, limit){
    page=page-1;
    let params = new HttpParams({
      fromObject:{
        table: 'movie',
        keyword: keyword,
        page: page,
        limit: limit
      }
    });
    return this.http.get('http://localhost:8080/search?',{params:params});
  }

  searchCelebrities(keyword, page, limit){
    let params = new HttpParams({
      fromObject:{
        table: 'celebrity',
        keyword: keyword,
      }
    });
    return this.http.get('http://localhost:8080/search',{params:params});
  }

  searchTV(keyword, page, limit){
    let params = new HttpParams({
      fromObject:{
        table: 'tv',
        keyword: keyword,
      }
    });
    return this.http.get('http://localhost:8080/search',{params:params});
  }
}
