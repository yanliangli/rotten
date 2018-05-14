import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

const httpOptions = {headers: new HttpHeaders({ 'Content-Type': 'application/json' })};
@Injectable()
export class SearchService{
  constructor(private http: HttpClient) { }

  searchMovies(keyword, page, itemsPerPage,sortBy, order){
    page=page-1;
    let params = new HttpParams({
      fromObject:{
        keyword: keyword,
        page: page,
        itemsPerPage: itemsPerPage,
        sortBy: sortBy,
        order: order,
      }
    });
    return this.http.get('http://localhost:8080/search/movie',{params:params});
  }

  searchCelebrities(keyword, page, itemsPerPage, sortBy, order){
    page=page-1;
    let params = new HttpParams({
      fromObject:{
        keyword: keyword,
        page: page,
        itemsPerPage: itemsPerPage,
        sortBy: sortBy,
        order: order,
      }
    });
    return this.http.get('http://localhost:8080/search/celebrity',{params:params});
  }

  searchTV(keyword, page, itemsPerPage, sortBy, order){
    page=page-1;
    let params = new HttpParams({
      fromObject:{
        keyword: keyword,
        page: page,
        itemsPerPage: itemsPerPage,
        sortBy: sortBy,
        order: order,
      }
    });
    return this.http.get('http://localhost:8080/search/tv',{params:params});
  }
}
