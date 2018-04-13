import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Movie} from '../models/movie.model';

const MOVIE_SERVER = 'http://localhost:8080/movie/';

@Injectable()
export class MovieDetailService {

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

  getMovieDetails(imdbId: string): any {
    return this.http.get(MOVIE_SERVER + imdbId);
  }

  rateMovie(hovered: number, imdbId: string) {
    return this.http.post(MOVIE_SERVER + imdbId + '/' + hovered, null);
  }
}
