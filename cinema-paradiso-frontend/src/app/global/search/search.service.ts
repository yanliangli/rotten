import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {log} from 'util';

const httpOptions = {headers: new HttpHeaders({ 'Content-Type': 'application/json' })};
@Injectable()
export class SearchService{
  constructor(private http: HttpClient) { }

  searchMovies(keyword){
    let params = new HttpParams({
      fromObject:{
        table: 'movie',
        keyword: keyword,
      }
    });
    console.log("http://localhost:8080/search?"+params.toString());
    return this.http.get('http://localhost:8080/search?',{params:params});
  }

  searchCelebrities(keyword){
    let params = new HttpParams({
      fromObject:{
        table: 'celebrity',
        keyword: keyword,
      }
    });
    return this.http.get('http://localhost:8080/search',{params:params});
  }
}
