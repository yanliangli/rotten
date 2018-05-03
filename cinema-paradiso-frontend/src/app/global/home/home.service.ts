import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class HomeService {

  public selectedImdbId: string;

  constructor(private http: HttpClient) { }

  getCarousel() {
    return this.http.get('http://localhost:8080/movie/carousel');
  }

  errorHandler(error): any {
    console.log(error);
    return Observable.throw(error.json.error || 'Server error');
  }

  getMoviesOpeningThisWeek() {
    return this.http.get('http://localhost:8080/movie/opening_this_week');
  }

  getMoviesComingSoon() {
    return this.http.get('http://localhost:8080/movie/coming_soon');
  }


  getMoviesTopBoxOffice() {
    return this.http.get('http://localhost:8080/movie/top_box_office');
  }

  getNewTVTonight(){
    return this.http.get('http://localhost:8080/tv/new_tv_tonight');
  }

  getMostPopularTVOnCP(){
    return this.http.get('http://localhost:8080/tv/most_popular');
  }

  parseImdbId(imdbId: string) {
    return this.selectedImdbId = imdbId;
  }

}
