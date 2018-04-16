import { Injectable } from '@angular/core';
import {HttpClient, HttpParams, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class AdminService {

  constructor(private http: HttpClient) { }

  getUsers() {
    return this.http.get('http://localhost:8080/admin/all_users');
  }
  getMovies() {
    return this.http.get('http://localhost:8080/movie/all');
  }
  getCelebrities() {
    return this.http.get('http://localhost:8080/celebrity/all_celebrities');
  }
  getReviews() {
    return this.http.get('http://localhost:8080/review/all_reviews');
  }
  getApplications() {
    return this.http.get('http://localhost:8080/admin/all_applications');
  }

  suspendUser(id: any) {
    return this.http.post('http://localhost:8080/user/suspend/'+id, null);
  }

  vertify(application: any) {
    return this.http.post('http://localhost:8080/user/vertify/critic', + application);
  }

  updateMovie(movie: any) {
    return this.http.post('http://localhost:8080/movie/update', + movie);
  }

  updateSlide(id: any, slide: any) {
    return this.http.post('http://localhost:8080/carsoul/slides/'+id, slide);
  }

  updateCelebrity(celebrity: any) {
    return this.http.post('http://localhost:8080/celebrity/update', celebrity);
  }

  deleteMovie(movieId: String) {
    return this.http.delete('http://localhost:8080/movie/delete/'+movieId);
  }


}
