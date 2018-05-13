import {Injectable} from '@angular/core';
import {HttpClient, HttpParams, HttpRequest} from '@angular/common/http';
import {Review} from '../../global/models/review.model';

@Injectable()
export class UserViewService {

  constructor(private http: HttpClient) {
  }


  getProfile(userId: number) {
    return this.http.get('http://localhost:8080/user/get/profile/' + userId);
  }

  getWishlist(userId: number) {
    return this.http.get('http://localhost:8080/wishlist/get/wishlist/' + userId);
  }
  getWatchlist(userId: number) {
    return this.http.get('http://localhost:8080/watchlist/get/watchlist/' + userId);
  }

  getRatedMovieList(userId: number) {
    return this.http.get('http://localhost:8080/user/getRatedMovies/' + userId);
  }

  getReviews(userId: number) {
    return this.http.get('http://localhost:8080/review/user/reviews/' + userId);
  }

  checkInFollower(userId: number){
    return this.http.get('http://localhost:8080/follower/check/' + userId).toPromise();
  }

  followUser(userId: number){
    return this.http.post('http://localhost:8080/follower/addFollowList/' + userId, null);
  }
  deFollowUser(userId: number){
    return this.http.delete('http://localhost:8080/follower/removeFromFollowList/' + userId);
  }
  getFollowers(userId: number){
    return this.http.get('http://localhost:8080/follower/get/followers/' + userId);
  }
  getFollowYou(userId: number){
    return this.http.get('http://localhost:8080/follower/get/followYou/' + userId);
  }

}
