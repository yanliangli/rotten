import { Component, OnInit } from '@angular/core';
import {Movie} from '../models/movie.model';
import {TV} from '../models/tv.model';
import {CategoriesService} from './categories.service';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss']
})
export class CategoriesComponent implements OnInit {

  page = 1;
  moviesOpeningTHisWeek: Movie[];
  moviesTopBoxOffice: Movie[];
  moviesComingSoon: Movie[];
  moviesTopRated: Movie[];

  moviesOpeningTHisWeekCount=0;
  moviesTopBoxOfficeCount=0;
  moviesComingSoonCount=0;
  moviesTopRatedCount=0;

  isMoviesOpeningTHisWeek: boolean;
  isMoviesTopBoxOffice: boolean;
  isMoviesComingSoon: boolean;
  isMoviesTopRated: boolean;

  tvNewTonight: TV[];
  tvMostPopularOnCP: TV[];
  tvTopRated: TV[];

  tvNewTonightCount=0;
  tvMostPopularOnCPCount=0;
  tvTopRatedCount=0;

  isTvNewTonight: boolean;
  isTvMostPopularOnCP: boolean;
  IsTvTopRated: boolean;



  constructor(private catService: CategoriesService) {
  }

  ngOnInit() {
  }

}
