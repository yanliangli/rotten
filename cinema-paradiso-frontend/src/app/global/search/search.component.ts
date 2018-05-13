import {Component, HostListener, OnInit} from '@angular/core';
import {SearchService} from './search.service';
import {Movie} from '../models/movie.model';
import {Celebrity} from '../models/celebrity.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {TypeVisitor} from '@angular/compiler/src/output/output_ast';
import {Page} from 'ngx-pagination/dist/pagination-controls.directive';
import {TV} from '../models/tv.model';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss'],
})
export class SearchComponent implements OnInit {
  keywordParam;
  movieResults:Movie[];
  movieSearchCount=0;
  celebritiesResults: Celebrity[];
  celebritySearchCount=0;
  tvResults: TV[];
  tvSearchCount=0;
  itemsPerPage=7;
  maxPageDisplay=7;
  moviePage:number = 1;
  tvPage: number = 1;
  celebrityPage:number = 1;
  movieLineCount;
  tvLineCont;
  celebrityLineCount;
  constructor(private route: ActivatedRoute, private searchService: SearchService, private router: Router) {
  }

  ngOnInit() {
    this.route.queryParamMap.subscribe((params: ParamMap)=>{
      this.keywordParam = params.get('keyword');
      this.searchForMovies();
      this.searchForCelebrities();
      this.searchForTV();
    });
  } //end onInit

  searchForMovies(){
    this.searchService.searchMovies(this.keywordParam, this.moviePage, this.itemsPerPage)
      .subscribe(
        data=>{
            if(data){
              console.log(data)
              this.movieResults = (data as Page[])['content'];
              this.movieSearchCount=(data as Page[])['totalElements'];
              this.movieLineCount = this.movieResults.length;
            }
          },
        error => console.log('Failed to fetch movie data')
      );
  }

  searchForCelebrities(){
    this.searchService.searchCelebrities(this.keywordParam, this.celebrityPage, this.itemsPerPage)
      .subscribe(
        data=>{
          if(data){
            console.log(data)
            this.celebritiesResults = (data as Page[])['content'];
            this.celebritySearchCount = (data as Page[])['totalElements'];
            this.celebrityLineCount = this.celebritiesResults.length;
          }
        },
        error => console.log('Failed to fetch celebrity data')
      );
  }

  searchForTV(){
    this.searchService.searchTV(this.keywordParam, this.tvPage, this.itemsPerPage)
      .subscribe(
        data=>{
          if(data){
            console.log(data)
            this.tvResults = (data as Page[])['content'];
            this.tvSearchCount = (data as Page[])['totalElements'];
            this.tvLineCont = this.tvResults.length;
          }
        },
        error => console.log('Failed to fetch tv data')
      );
  }
}
