import {Injectable} from '@angular/core';
import {HttpClient, HttpParams, HttpRequest} from '@angular/common/http';
import {Review} from '../../global/models/review.model';

@Injectable()
export class RegUserService {

  constructor(private http: HttpClient) {
  }

  upload(formData: FormData) {
    const req = new HttpRequest('POST', 'http://localhost:8080/user/update/avatar', formData, {
      reportProgress: true,
      responseType: 'text'
    });
    return this.http.request(req);
  }

  getProfile() {
    return this.http.get('http://localhost:8080/user/get/profile');
  }

  update(profile: any) {
    return this.http.post('http://localhost:8080/user/update/profile', profile);
  }

  changePassword(oldPassword: string, newPassword: string) {
    const params = new HttpParams().set('old_password', oldPassword).set('new_password', newPassword);
    return this.http.post('http://localhost:8080/user/change/password', params);
  }
  getWishlistMovies() {
    return this.http.get('http://localhost:8080/wishlist/get/wishlistMovies');
  }
  getWatchlistMovies() {
    return this.http.get('http://localhost:8080/watchlist/get/watchlistMovies');
  }
  getWishlistTvs() {
    return this.http.get('http://localhost:8080/wishlist/get/wishlistTvs');
  }
  getWatchlistTvs() {
    return this.http.get('http://localhost:8080/watchlist/get/watchlistTvs');
  }

  getRatedMovieList() {
    return this.http.get('http://localhost:8080/user/getRatedMovies');
  }

  getReviews() {
    return this.http.get('http://localhost:8080/review/user/reviews');
  }

  deleteReview(imbdId: string, reviewId: number) {
    return this.http.delete('http://localhost:8080/review/deleteReview/' + imbdId + '/' + reviewId);
  }
  editReview(imbdId: string, review: Review) {
    alert('coming');
    return this.http.post('http://localhost:8080/review/updateReview/' + imbdId, review );
  }
  deleteUser() {
    return this.http.delete('http://localhost:8080/user/deleteUser');
  }
  getFollowers(){
    return this.http.get('http://localhost:8080/follower/get/followers');
  }
  getFollowYou(){
    return this.http.get('http://localhost:8080/follower/get/followYou');
  }
}
