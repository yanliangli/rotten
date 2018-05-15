import {Component, OnInit, Input} from '@angular/core';
import {NgbRatingConfig} from '@ng-bootstrap/ng-bootstrap';
import {Movie} from '../models/movie.model';
import {TvDetailService} from './tv-detail.service';
import {MovieService} from '../movie/movie.service';
import {ActivatedRoute} from '@angular/router';
import {LoginStatusService} from '../login/login.status.service';
import {Token} from '../login/token.model';
import {RegUserService} from '../../user/reg-user/reg-user.service';
import {JwtHelperService} from '@auth0/angular-jwt';
import {Review} from '../models/review.model';
import {Celebrity} from '../models/celebrity.model';
import {post} from 'selenium-webdriver/http';
import {Trailer} from '../models/trailer.model';
import {DomSanitizer} from '@angular/platform-browser';
import {TV} from '../models/tv.model';
import {ToastrService} from 'ngx-toastr';

class Profile {
  name: string;
  id: number;
  profileImage: string;
  biography: string;
  isCritic: boolean;
  username: string;
  email: string;
}

@Component({
  selector: 'app-tv-detail',
  templateUrl: './tv-detail.component.html',
  styleUrls: ['./tv-detail.component.scss'],
})
export class TvDetailComponent implements OnInit {
  status: boolean;
  inWatchlist: boolean;
  inWishlist: boolean;
  inRatedMovieList: boolean;
  user: Token;
  tv: TV;
  sub: any;
  selectedMovieId: string;
  selected = 0;
  hovered = 0;
  textValue: string;
  profile = new Profile();
  tokenHelper = new JwtHelperService();
  profile_url: string;
  isCritic: boolean;
  review = new Review();
  casts: Celebrity[] = [];
  myTrailers: string[] = [];
  constructor(config: NgbRatingConfig,
              private movieService: MovieService,
              private tvDetailService: TvDetailService,
              private loginStatusService: LoginStatusService,
              private regUserService: RegUserService,
              private route: ActivatedRoute,
              private sanitizer: DomSanitizer,
              private toastr: ToastrService) {
    this.profile_url = 'http://localhost:8080/user/avatar/default.jpeg';
    this.loginStatusService.currentStatus.subscribe(state => {
      this.status = state;
      console.log('current login state ', this.status);


      if (this.status) {
        $('.modal-wrapper').hide();
        $('.page-wrapper').hide();
        this.user = this.loginStatusService.getTokenDetails() as Token;
        this.checkInWatchlist();
        this.checkInWishlist();
        this.getRatedMovie();
      }
      this.regUserService.getProfile().subscribe(profileDetails => {
        console.log(profileDetails);
        this.profile = profileDetails as Profile;
        const decodedToken = this.tokenHelper.decodeToken(localStorage.getItem('token'));
        this.profile.email = decodedToken['email'];
        this.profile.id = decodedToken['profileId'];
        this.profile.username = decodedToken['username'];
        this.profile.profileImage = profileDetails['profileImage'];
        this.isCritic = this.profile.isCritic;
        if (this.profile.profileImage === undefined) {
          this.profile_url = 'http://localhost:8080/user/avatar/default.jpeg';
        } else {
          this.profile_url = 'http://localhost:8080/user/avatar/' + profileDetails['profileImage'];
        }
      });
    });
    // customize default values carousel slider
    config.max = 5;
    config.readonly = true;
  }

  addReview() {
    this.tvDetailService.addReview(this.selectedMovieId, this.review).subscribe(result => {
      console.log(result);
      this.getMovie(this.selectedMovieId);
      this.toastr.success('Success');
    });
  }
  getReview() {

  }
  addWishlist() {
    this.tvDetailService.addToWishlist(this.selectedMovieId).subscribe(result => {
      console.log(result);
      this.checkInWishlist();
      this.toastr.success('Success');
    });
  }
  addWatchlist() {
    this.tvDetailService.addToWatchlist(this.selectedMovieId).subscribe(result => {
      console.log(result);
      this.checkInWatchlist();
      this.toastr.success('Success');
    });
  }

  removeFromWishList() {
    this.tvDetailService.removeFromWishList(this.selectedMovieId).subscribe(result => {
      console.log(result);
      this.checkInWishlist();
      this.toastr.success('Success');
    });
  }

  removeFromWacthList() {
    this.tvDetailService.removeFromWacthList(this.selectedMovieId).subscribe(result => {
      console.log(result);
      this.checkInWatchlist();
      this.toastr.success('Success');
    });
  }

  rateMovie() {
    this.tvDetailService.rateMovie(this.hovered, this.selectedMovieId).subscribe(result => {
      console.log(result);
      this.getRatedMovie();
      this.toastr.success('Success');
    });
  }
  deleteRating() {
    this.tvDetailService.deleteRating(this.selectedMovieId).subscribe(result => {
      console.log(result);
      this.getRatedMovie();
      this.toastr.success('Success');
    });
  }
  getRatedMovie(): any {
    this.tvDetailService.getRatedMovie(this.selectedMovieId)
      .subscribe(
        data => {
          // console.log(data);
          this.selected = data as number;
          if (this.selected !== 0) {
            this.inRatedMovieList = true;
            console.log(this.inRatedMovieList);
          } else {
            this.inRatedMovieList = false;
            console.log(this.inRatedMovieList);
          }
          console.log(this.selected);
        },
        error => console.log('Failed to fetch carousel data')
      );
  }
  checkInWishlist():any{
    this.tvDetailService.checkInWishlist(this.selectedMovieId).then(result => {
        if (result === true) {
          this.inWishlist = true;
          console.log(this.inWishlist);
        } else {
          this.inWishlist = false;
          console.log(this.inWishlist);
        }
      }
    );
  }
  checkInWatchlist():any{
    this.tvDetailService.checkInWatchlist(this.selectedMovieId).then(result => {
        if (result === true) {
          this.inWatchlist = true;
          console.log(this.inWatchlist);
        } else {
          this.inWatchlist = false;
          console.log(this.inWatchlist);
        }
      }
    );
  }
  notice() {
    alert('Please Log in/Sign up first!');
  }

  ngOnInit() {
    // TODO: get data from route instead of from movieService.movieIdObservable
    // this.sub = this.route
    //   .data
    //   .subscribe(v => console.log(v)
    //   );
    this.selectedMovieId = this.route.snapshot.params['id'];

    if (this.loginStatusService.getTokenDetails() !== null) {
      this.loginStatusService.changeStatus(true);
    }
    console.log('id: ' + this.selectedMovieId);
    this.getMovie(this.selectedMovieId);

    this.ratingAnimation();
    console.log('user token ', this.loginStatusService.getTokenDetails());


  }

  getMovie(imdbId: string): any {
    console.log('Selected movie: ' + imdbId);
    this.tvDetailService.getMovieDetails(imdbId)
      .subscribe(
        data => {
          console.log(data);
          this.tv = data as TV;
          this.getCast(this.tv);
          this.getTrailerData(this.tv.trailers);
        },
        error => console.log('Failed to fetch movie with id')
      );

  }

  getCast(tv:TV):any{
    this.tvDetailService.getCelebrityByName(tv.cast)
      .subscribe(
        data => {
          this.casts = data as Celebrity[];
          this.checkEmptyProfilePic();
        },
        error => console.log('Failed to fetch celebrity by name')
      );
  }

  checkEmptyProfilePic(){
    let i : number
    for(i=0; i<this.casts.length; i++){
      if((this.casts[i]).poster== "N/A"){
        this.casts[i].poster = "http://valmorgan.co.nz/wp-content/uploads/2016/06/default-movie-1-3.jpg"
      }
    }
  }

  // TODO: temp method
  ratingAnimation():void{
    $('.bar span').hide();
    $('#bar-five').animate({
      width: '100%'
    }, 1000);
    $('#bar-four').animate({
      width: '90%'
    }, 1000);
    $('#bar-three').animate({
      width: '60%'
    }, 1000);
    $('#bar-two').animate({
      width: '50%'
    }, 1000);
    $('#bar-one').animate({
      width: '20%'
    }, 1000);

    $('#bar-ten').animate({
      width: '100%'
    }, 1000);
    $('#bar-nine').animate({
      width: '90%'
    }, 1000);
    $('#bar-eight').animate({
      width: '60%'
    }, 1000);
    $('#bar-seven').animate({
      width: '50%'
    }, 1000);
    $('#bar-six').animate({
      width: '20%'
    }, 1000);

    setTimeout(function () {
      $('.bar span').fadeIn('slow');
    }, 500);
  }

  numberInMillion(labelValue:any) {
    if(labelValue == null){
      return "N/A";
    }
    // Nine Zeroes for Billions
    return Math.abs(Number(labelValue)) >= 1.0e+9 ? (Math.abs(Number(labelValue)) / 1.0e+9).toFixed(2) + "B"
      // Six Zeroes for Millions
      : Math.abs(Number(labelValue)) >= 1.0e+6 ? (Math.abs(Number(labelValue)) / 1.0e+6).toFixed(2) + "M"
        // Three Zeroes for Thousands
        : Math.abs(Number(labelValue)) >= 1.0e+3 ? (Math.abs(Number(labelValue)) / 1.0e+3).toFixed(2) + "K"
          : Math.abs(Number(labelValue));
  }

  getTrailerData(trailerIds : string[]){
    let prefix = "https://www.imdb.com/video/imdb/";
    let postfix = "/imdb/embed?autoplay=false&width=400";

    let i;
    for(i=0;i<trailerIds.length;i++) {
      let x = prefix+trailerIds[i]+postfix;
      this.myTrailers.push(x);
    }
  }

  trailerURL(str:string){
    return this.sanitizer.bypassSecurityTrustResourceUrl(str);
  }
}



