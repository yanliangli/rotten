import {Component, OnInit, ViewChild} from '@angular/core';
import * as $ from 'jquery';
import {LoginStatusService} from '../login/login.status.service';
import {HomeService} from './home.service';
import {Movie} from '../models/movie.model';
import {Celebrity} from '../models/celebrity.model';
import {Carousel} from '../models/carousel.model';
import {log} from 'util';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {NgbCarouselConfig} from '@ng-bootstrap/ng-bootstrap';
import {CarouselSlide} from '../models/carouselSlide.model';
import {MovieDetailService} from '../movie-detail/movie-detail.service';
import {MovieService} from '../movie/movie.service';
import {TV} from '../models/tv.model';
import {Page} from 'ngx-pagination/dist/pagination-controls.directive';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {

  currentRate = 3.14;

  // TODO:should create Slide[]
  carousel: Movie[];
  moviesOpening: Movie[];
  moviesTopBoxOffice: Movie[];
  moviesComingSoon: Movie[];
  selectedMovieId: string;
  newTVTonight: TV[];
  mostPopularTVOnCP: TV[];
  constructor(private loginStatusService: LoginStatusService,
              private homeService: HomeService,
              private movieService: MovieService,
              config: NgbCarouselConfig) {
    config.interval = 3000;
    config.wrap = true;
    config.keyboard = false;
  }

  ngOnInit() {
    // load carousel
    this.getCarousel();
    // load movies playing
    this.getMoviesOpeningThisWeek();
    this.getMoviesComingSoon();
    this.getMoviesTopBoxOffice();
    this.getNewTVTonight();
    this.getMostPopularTVOnCP()

    if (this.loginStatusService.getTokenDetails() !== null) {
      this.loginStatusService.changeStatus(true);
    }
    this.movieService.movieIdObservable.subscribe(observable => this.selectedMovieId = observable);
  }

  getCarousel(): any {
    this.homeService.getCarousel()
      .subscribe(
        data => {
          // console.log(data);
          this.carousel = data as Movie[];
          console.log(this.carousel);
        },
        error => console.log('Failed to fetch carousel data')
      );
  }

  getMoviesOpeningThisWeek(): any {
    this.homeService.getMoviesOpeningThisWeek(1,6, "", "")
      .subscribe(
        data => {
          this.moviesOpening = (data as Page[])['content'];
          console.log(this.moviesOpening);
        },
        error => console.log('Failed to fetch movies opening this week')
      );
  }

  getMoviesComingSoon(): any {
    this.homeService.getMoviesComingSoon(1,6, "", "")
      .subscribe(
        data => {
          this.moviesComingSoon = (data as Page[])['content'];
          console.log(this.moviesComingSoon);
        }
        ,
        error => console.log('Failed to fetch movies coming soon')
      );
  }

  getMoviesTopBoxOffice(): any {
    this.homeService.getMoviesTopBoxOffice(1,6, "", "")
      .subscribe(
        data => {
          this.moviesTopBoxOffice = (data as Page[])['content'];
          console.log(this.moviesTopBoxOffice);
         // this.moviesTopBoxOffice.sort(function(a,b){return b.boxOffice - a.boxOffice});
        },
        error => console.log('Failed to fetch movies top office')
      );
  }

  getNewTVTonight(): any {
    this.homeService.getNewTVTonight(1,6)
      .subscribe(
        data => {
          this.newTVTonight = (data as Page[])['content'];
          console.log(this.newTVTonight);
        },
        error => console.log('Failed to fetch new tv tonight')
      );
  }

  getMostPopularTVOnCP(): any {
    this.homeService.getMostPopularTVOnCP(1,6)
      .subscribe(
        data => {
          this.mostPopularTVOnCP = (data as Page[])['content'];
          console.log(this.mostPopularTVOnCP);
        },
        error => console.log('Failed to fetch most popular tv on cinema paradiso')
      );
  }

  // pass the selected movie id to movie detail page for rendering
  setImdbId(imdbId: string) {
    this.movieService.setSelectedMovieId(imdbId);
  }

  convertStringToDate(d:Date){
    let date:Date;
    date = new Date(d.toString());
    return date.toLocaleDateString("en-En", {month:'short', day:'numeric'});
  }

  convertNumberToMillion(n:any){
    return ("$"+this.numberInMillion(n));
  }

  numberInMillion(labelValue:any) {
    if(labelValue == null){
      return "N/A";
    }
    else {
      // Nine Zeroes for Billions
      return Math.abs(Number(labelValue)) >= 1.0e+9 ? (Math.abs(Number(labelValue)) / 1.0e+9).toFixed(2) + "B"
        // Six Zeroes for Millions
        : Math.abs(Number(labelValue)) >= 1.0e+6 ? (Math.abs(Number(labelValue)) / 1.0e+6).toFixed(2) + "M"
          // Three Zeroes for Thousands
          : Math.abs(Number(labelValue)) >= 1.0e+3 ? (Math.abs(Number(labelValue)) / 1.0e+3).toFixed(2) + "K"
            : Math.abs(Number(labelValue));
    }
  }

}

