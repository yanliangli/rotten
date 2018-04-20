import {Component, OnInit, Input} from '@angular/core';
import {NgbRatingConfig} from '@ng-bootstrap/ng-bootstrap';
import {Movie} from '../models/movie.model';
import {MovieDetailService} from './movie-detail.service';
import {MovieService} from '../movie/movie.service';
import {ActivatedRoute} from '@angular/router';
import {LoginStatusService} from '../login/login.status.service';
import {Token} from '../login/token.model';
import {RegUserService} from '../../user/reg-user/reg-user.service';
import {JwtHelperService} from '@auth0/angular-jwt';
import {Review} from '../models/review.model';

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
  selector: 'app-movie-detail',
  templateUrl: './movie-detail.component.html',
  styleUrls: ['./movie-detail.component.scss'],
  providers: [MovieDetailService, RegUserService]
})
export class MovieDetailComponent implements OnInit {

  status: boolean;
  inWatchlist: boolean;
  inWishlist: boolean;
  inRatedMovieList: boolean;
  user: Token;
  movie: Movie;
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
  constructor(config: NgbRatingConfig,
              private movieService: MovieService,
              private movieDetailService: MovieDetailService,
              private loginStatusService: LoginStatusService,
              private regUserService: RegUserService,
              route: ActivatedRoute) {

    this.selectedMovieId = route.snapshot.params['id'];
    this.loginStatusService.currentStatus.subscribe(state => {
      this.status = state;
      console.log('current login state ', this.status);


      if (this.status) {
        $('.modal-wrapper').hide();
        $('.page-wrapper').hide();
        this.user = this.loginStatusService.getTokenDetails() as Token;
        movieDetailService.checkInWatchlist(this.selectedMovieId).then(result => {
            if (result === true) {
              this.inWatchlist = true;
              console.log(this.inWatchlist);
            } else {
              this.inWatchlist = false;
              console.log(this.inWatchlist);
            }
          }
        );
        movieDetailService.checkInWishlist(this.selectedMovieId).then(result => {
            if (result === true) {
              this.inWishlist = true;
              console.log(this.inWishlist);
            } else {
              this.inWishlist = false;
              console.log(this.inWishlist);
            }
          }
        );
        this.getRatedMovie();
            if (this.selected !== 0) {
              this.inRatedMovieList = true;
              console.log(this.inRatedMovieList);
            } else {
              this.inRatedMovieList = false;
              console.log(this.inRatedMovieList);
            }
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
    // this.review.reviewContent = this.textValue;
    this.movieDetailService.addReview(this.selectedMovieId, this.review).subscribe(result => {
      console.log(result);
      location.reload(true);
    });
  }
  getReview() {

  }
  addWishlist() {
    this.movieDetailService.addToWishlist(this.selectedMovieId).subscribe(result => {
      console.log(result);
      location.reload(true);
    });
  }
  addWatchlist() {
    this.movieDetailService.addToWatchlist(this.selectedMovieId).subscribe(result => {
      console.log(result);
      location.reload(true);
    });
  }

  removeFromWishList() {
    this.movieDetailService.removeFromWishList(this.selectedMovieId).subscribe(result => {
      console.log(result);
      location.reload(true);
    });
  }

  removeFromWacthList() {
    this.movieDetailService.removeFromWacthList(this.selectedMovieId).subscribe(result => {
      console.log(result);
      location.reload(true);
    });
  }

  rateMovie() {
    this.movieDetailService.rateMovie(this.hovered, this.selectedMovieId).subscribe(result => {
      console.log(result);
      location.reload(true);
    });
  }
  getRatedMovie(): any {
    this.movieDetailService.getRatedMovie(this.selectedMovieId)
      .subscribe(
        data => {
          // console.log(data);
            this.selected = data as number;
          console.log(this.selected);
        },
        error => console.log('Failed to fetch carousel data')
      );
  }


  ngOnInit() {
    // TODO: get data from route instead of from movieService.movieIdObservable
    // this.sub = this.route
    //   .data
    //   .subscribe(v => console.log(v)
    //   );


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
    this.movieDetailService.getMovieDetails(imdbId)
      .subscribe(
        data => {
          console.log(data);
          this.movie = data as Movie;
        },
        error => console.log('Failed to fetch movie with id')
      );
  }

  // TODO: temp method
  ratingAnimation(): void {
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
}
