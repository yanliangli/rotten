import { Injectable } from '@angular/core';
import {Http, Response, Headers, RequestOptions} from "@angular/http";
import {HttpClient, HttpParams, HttpRequest, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Observable} from "rxjs/Rx";
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable()
export class AdminService {

  constructor(private http: HttpClient) { }
  // manage movies
  getMovies(){
    return this.http.get('http://localhost:8080/movie/all');
  }
  createMovie(movie){
    let body = JSON.stringify(movie);
    return this.http.post('http://localhost:8080/movie/add', body, httpOptions)
  }
<<<<<<< HEAD

  updateMovie(movie){
    let body = JSON.stringify(movie);
    return this.http.post('http://localhost:8080/movie/update', body, httpOptions)
  }

=======
  updateMovie(movie){
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify(movie);
    return this.http.post('http://localhost:8080/movie/update', +body).map((res:Response)=> res.json());
  }
>>>>>>> refs/remotes/origin/master
  deleteMovie(movieId:String){
    return this.http.delete('http://localhost:8080/movie/delete/'+movieId);
  }

  // manage users
  getUsers(){
    return this.http.get('http://localhost:8080/admin/all_users');
  }
  deleteUser(userId:any){
    return this.http.delete('http://localhost:8080/user/delete/'+userId);
<<<<<<< HEAD
  }
  suspendUser(id:any) {
    return this.http.post('http://localhost:8080/user/suspend/'+id, null);
  }
=======
  }
  suspendUser(id:any) {
    return this.http.post('http://localhost:8080/user/suspend/'+id, null);
  }
>>>>>>> refs/remotes/origin/master

  // manage celebrities
  getCelebrities(){
    return this.http.get('http://localhost:8080/celebrity/all_celebrities');
  }
  updateCelebrity(celebrity:any){
    return this.http.post('http://localhost:8080/celebrity/update', celebrity);
  }
  deleteCelebrity(celebrityId:String){
    return this.http.delete('http://localhost:8080/celebrity/delete/'+celebrityId);
  }

  //manage applications
  getApplications(){
    return this.http.get('http://localhost:8080/admin/all_applications');
  }
  vertify(application:any){
    return this.http.post('http://localhost:8080/user/vertify/critic', + application);
  }

  //manage reviews
  getReviews(){
    return this.http.get('http://localhost:8080/review/all_reviews');
  }
  deleteReview(reviewId:any){
    return this.http.delete('http://localhost:8080/review/delete/'+reviewId);
  }


  //manage carousels
  updateSlide(id:any, slide:any){
    return this.http.post('http://localhost:8080/carousel/slides/'+id, slide);
  }





}
