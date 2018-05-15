import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Movie} from '../models/movie.model';

import {Review} from '../models/review.model';

const TV_SERVER = 'http://localhost:8080/tv/';

@Injectable()
export class TvDetailService {

  constructor(private http: HttpClient) {
  }
  checkInWishlist(imdbId: string) {
    return this.http.get('http://localhost:8080/wishlist/check/' + imdbId).toPromise();
  }
  checkInWatchlist(imdbId: string) {
    return this.http.get('http://localhost:8080/watchlist/check/' + imdbId).toPromise();
  }

  addToWishlist(imdbId: string) {
    const params = new HttpParams().set('filmId', imdbId);
    return this.http.post('http://localhost:8080/wishlist/addWishlist', params);
  }

  addToWatchlist(imdbId: string) {
    const params = new HttpParams().set('filmId', imdbId);
    return this.http.post('http://localhost:8080/watchlist/addWatchlist', params);
  }

  removeFromWishList(imdbId: string) {
    return this.http.delete('http://localhost:8080/wishlist/' + imdbId);
  }

  removeFromWacthList(imdbId: string) {
    return this.http.delete('http://localhost:8080/watchlist/' + imdbId);
  }

  getMovieDetails(imdbId: string): any {
    return this.http.get(TV_SERVER + imdbId);
  }

  getCelebrityByName(nameList : string[]): any{
    return this.http.get("http://localhost:8080/celebrity/get/" + nameList);
  }

  rateMovie(hovered: number, imdbId: string) {
    return this.http.post(TV_SERVER + imdbId + '/' + hovered, null);
  }

  deleteRating(imdbId: string) {
    return this.http.delete(TV_SERVER + imdbId);
  }

  getRatedMovie(imdbId: string) {
    return this.http.get('http://localhost:8080/user/getRatedMovie/' + imdbId);
  }

  addReview(imdbId: string, review: Review) {
    return this.http.post('http://localhost:8080/review/addReview/' + imdbId , review);
  }
}
