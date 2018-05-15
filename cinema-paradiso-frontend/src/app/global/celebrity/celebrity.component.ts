import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {CelebrityService} from './celebrity.service';
import {Page} from 'ngx-pagination/dist/pagination-controls.directive';
import {Celebrity} from '../models/celebrity.model';
import {Movie} from '../models/movie.model';
import {TV} from '../models/tv.model';

@Component({
  selector: 'app-celebrity',
  templateUrl: './celebrity.component.html',
  styleUrls: ['../movie-detail/movie-detail.component.scss', './celebrity.component.scss']
})
export class CelebrityComponent implements OnInit {
  selectedCelebrityId:string;
  celebrity:Celebrity;
  movies_in:Movie[];
  tv_in:TV[];
  constructor(private route: ActivatedRoute, private celebrityService:CelebrityService) { }

  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap)=>{
      this.selectedCelebrityId = params.get('id')
      this.getCelebrity(this.selectedCelebrityId);
    });
  }

  getCelebrity(imdbId:string){
    this.celebrityService.getCelebrity(imdbId).subscribe(
      data=>{
        if(data){
          this.celebrity = data as Celebrity;
          console.log(this.celebrity);
          this.getMoviesIn(this.celebrity);
          this.getTVIn(this.celebrity);
        }
      },
      error => console.log('Failed to fetch celebrity data')
    );
  }

  getMoviesIn(celebrity: Celebrity){
    this.celebrityService.getFilmographyMovies(celebrity).subscribe(
      data=>{
        if(data){
          this.movies_in = data as Movie[];
          console.log(this.movies_in);
        }
      },
      error => console.log('Failed to fetch movie data')
    );
  }

  getTVIn(celebrity: Celebrity){
    this.celebrityService.getFilmographyTV(celebrity).subscribe(
      data=>{
        if(data){
          this.tv_in = data as TV[];
          console.log(this.tv_in);
        }
      },
      error => console.log('Failed to fetch tv data')
    );
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
