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

  getMoviesPlaying() {
    return this.http.get('http://localhost:8080/movie/playing');
  }

  getMoviesTrending() {
    return this.http.get('http://localhost:8080/movie/trending');
  }

  getMoviesTopBoxOffice() {
    return this.http.get('http://localhost:8080/movie/top_box_office');
  }

  getMoviesTopRating() {
    return this.http.get('http://localhost:8080/movie/top_rating');
  }

  getMoviesSimilar(movieId:String) {
    return this.http.get('http://localhost:8080/similar/'+movieId);
  }

  getMoviesInRange(start:String, end:String) {
    return this.http.get('http://localhost:8080/movie/in_range/'+start+"/"+end);
  }


  parseImdbId(imdbId: string) {
    return this.selectedImdbId = imdbId;
  }

}
