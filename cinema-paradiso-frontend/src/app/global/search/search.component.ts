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
  providers: [SearchService]
})
export class SearchComponent implements OnInit {
  keywordParam: string;
  movieResults:Movie[];
  movieCount=0;
  celebritiesResults: Celebrity[];
  celebrityCount=0;
  tvResults: TV[];
  tvCount=0;
  searchPeopleBoolean = true;
  searchMovieBoolean = true;
  searchTVBoolean = true;
  itemsPerPage=7;
  maxPageDisplay=7;
  moviePage:number=1;
  tvPage: number = 1;
  celebrityPage:number = 1;
  constructor(private route: ActivatedRoute, private searchService: SearchService, private router: Router) {

  }
  ngOnInit() {
    this.route.queryParamMap.subscribe((params: ParamMap)=>{
      this.keywordParam = params.get('keyword');
      this.searchForMovies();
      this.searchForCelebrities();
      this.searchForTV();
      this.showAllResults();
      $('.movie_results').show();
      $('.people_results').show();
      $('.tv_results').show();
    });

    $('.show_all').click(function (e) {
      e.preventDefault();
      $('.movie_results').show();
      $('.people_results').show();
      $('.tv_results').show();
    });

    $('.show_movies').click(function (e) {
      e.preventDefault();
      $('.movie_results').show();
      $('.people_results').hide();
      $('.tv_results').hide();
    });

    $('.show_tv').click(function (e) {
      e.preventDefault();
      $('.movie_results').hide();
      $('.people_results').hide();
      $('.tv_results').show();
    });

    $('.show_people').click(function (e) {
      e.preventDefault();
      $('.movie_results').hide();
      $('.people_results').show();
      $('.tv_results').hide();
    });

    this.showAllResults();
  } //end onInit

  searchForMovies(){
    console.log("page: ", this.moviePage)
    this.searchService.searchMovies(this.keywordParam, this.moviePage, this.itemsPerPage)
      .subscribe(
        data=>{
            if(data){
              console.log(data)
              this.movieResults = (data as Page[])['content'];
              this.movieCount=(data as Page[])['totalElements'];
              if(this.movieCount==0){
                this.searchMovieBoolean=false;
              }
              else{
                this.searchMovieBoolean=true;
              }
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
            this.celebrityCount = (data as Page[])['totalElements'];
            if(this.celebrityCount==0){
              this.searchPeopleBoolean=false;
            }
            else{
              this.searchPeopleBoolean=true;
            }
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
            this.tvCount = (data as Page[])['totalElements'];
            if(this.tvCount==0){
              this.searchTVBoolean=false;
            }
            else{
              this.searchTVBoolean=true;
            }
          }
        },
        error => console.log('Failed to fetch tv data')
      );
  }

  onlyShowPeopleResults(){
    this.searchPeopleBoolean = true;
    this.searchMovieBoolean = false;
    this.searchTVBoolean = false;
  }

  onlyShowMovieResults(){
    this.searchPeopleBoolean = false;
    this.searchMovieBoolean = true;
    this.searchTVBoolean = false;
  }

  onlyShowTVResults(){
    this.searchPeopleBoolean = false;
    this.searchMovieBoolean = false;
    this.searchTVBoolean = true;
  }

  showAllResults(){
      this.searchPeopleBoolean = true;
      this.searchMovieBoolean = true;
      this.searchTVBoolean = true;
  }

}
