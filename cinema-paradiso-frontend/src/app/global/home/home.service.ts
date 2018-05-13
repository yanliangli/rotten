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

  getMoviesOpeningThisWeek(page, itemsPerPage, sortBy, order) {
    page = page -1;
    const params = new HttpParams().set('page', page).set('itemsPerPage', itemsPerPage).set('sortBy', sortBy).set('order', order);
    return this.http.post('http://localhost:8080/movie/opening_this_week/',params);
  }

  getMoviesComingSoon(page, itemsPerPage, sortBy, order) {
    page = page -1;
    const params = new HttpParams().set('page', page).set('itemsPerPage', itemsPerPage).set('sortBy', sortBy).set('order', order);
    return this.http.post('http://localhost:8080/movie/coming_soon',params);
  }

  getMoviesTopBoxOffice(page, itemsPerPage, sortBy, order) {
    page = page -1;
    const params = new HttpParams().set('page', page).set('itemsPerPage', itemsPerPage).set('sortBy', sortBy).set('order', order);
    return this.http.post('http://localhost:8080/movie/top_box_office',params);
  }

  getAcademyAward(page, itemsPerPage, sortBy, order) {
    page = page -1;
    const params = new HttpParams().set('page', page).set('itemsPerPage', itemsPerPage).set('sortBy', sortBy).set('order', order);
    return this.http.post('http://localhost:8080/movie/oscar_best_picture',params);
  }

  getTopRatedMovie(page, itemsPerPage, sortBy, order) {
    page = page -1;
    const params = new HttpParams().set('page', page).set('itemsPerPage', itemsPerPage).set('sortBy', sortBy).set('order', order);
    return this.http.post('http://localhost:8080/movie/top_rating',params);
  }

  getMoviesCertifiedFresh(page, itemsPerPage, sortBy, order){
    page = page -1;
    const params = new HttpParams().set('page', page).set('itemsPerPage', itemsPerPage).set('sortBy', sortBy).set('order', order);
    return this.http.post('http://localhost:8080/movie/certified_fresh',params);
  }

  getNewTVTonight(page, itemsPerPage){
    page = page -1;
    const params = new HttpParams().set('page', page).set('itemsPerPage', itemsPerPage);
    return this.http.post('http://localhost:8080/tv/new_tv_tonight',params);
  }

  getMostPopularTVOnCP(page, itemsPerPage){
    page = page -1;
    const params = new HttpParams().set('page', page).set('itemsPerPage', itemsPerPage);
    return this.http.post('http://localhost:8080/tv/most_popular',params);
  }

  getTVCertifiedFresh(page, itemsPerPage){
    page = page -1;
    const params = new HttpParams().set('page', page).set('itemsPerPage', itemsPerPage);
    return this.http.post('http://localhost:8080/tv/certified_fresh',params);
  }

  getTopRatedTV(page, itemsPerPage){
    page = page -1;
    const params = new HttpParams().set('page', page).set('itemsPerPage', itemsPerPage);
    return this.http.post('http://localhost:8080/tv/top_rating',params);
  }

  parseImdbId(imdbId: string) {
    return this.selectedImdbId = imdbId;
  }

}
