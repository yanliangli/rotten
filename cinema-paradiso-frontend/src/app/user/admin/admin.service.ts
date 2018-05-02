import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

const httpOptions = {headers: new HttpHeaders({ 'Content-Type': 'application/json' })};
@Injectable()
export class AdminService {
  constructor(private http: HttpClient) { }
  // manage movies
  getMovies(){
    return this.http.get('http://localhost:8080/movie/all');
  }
  createMovie(movie){
    let body = JSON.stringify(movie);
    return this.http.post('http://localhost:8080/movie/add', body, httpOptions);
  }
  updateMovie(movie){
    let body = JSON.stringify(movie);
    return this.http.post('http://localhost:8080/movie/update', body, httpOptions);
  }
  deleteMovie(movieId){
    return this.http.delete('http://localhost:8080/movie/delete/'+movieId);
  }
  // manage users
  getUsers() {
    return this.http.get('http://localhost:8080/admin/all_users');
  }
  suspendUser(id: any) {
    return this.http.post('http://localhost:8080/user/suspend/'+id, null);
  }

  // manage celebrities
  getCelebrities() {
    return this.http.get('http://localhost:8080/celebrity/all_celebrities');
  }
  updateCelebrity(celebrity) {
    return this.http.post('http://localhost:8080/celebrity/update', celebrity);
  }
  deleteCelebrity(celebrityId){
    return this.http.delete('http://localhost:8080/movie/delete/'+celebrityId);
  }

  // manage reviews
  getReviews() {
    return this.http.get('http://localhost:8080/review/all_reviews');
  }

  // manage applications
  getApplications() {
    return this.http.get('http://localhost:8080/admin/all_applications');
  }
  vertify(application: any) {
    return this.http.post('http://localhost:8080/user/vertify/critic', + application);
  }

  // manage carousels
  updateSlide(id: any, slide: any) {
    return this.http.post('http://localhost:8080/carousel/slides/'+id, slide);
  }





}
