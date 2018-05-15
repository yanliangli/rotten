import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Celebrity} from '../models/celebrity.model';

const httpOptions = {headers: new HttpHeaders({ 'Content-Type': 'application/json' })};
@Injectable()
export class CelebrityService {

  constructor(private http: HttpClient) { }

  getCelebrity(imdbId:string) {
    return this.http.get('http://localhost:8080/celebrity/'+imdbId);
  }

  getFilmographyMovies(celebrity:Celebrity){
    let body = JSON.stringify(celebrity);
    return this.http.post('http://localhost:8080/celebrity/in_movie/',body,httpOptions);
  }

  getFilmographyTV(celebrity:Celebrity){
    let body = JSON.stringify(celebrity);
    return this.http.post('http://localhost:8080/celebrity/in_tv/', body,httpOptions);
  }

}
