import {Component, HostListener, OnInit} from '@angular/core';
import {SearchService} from './search.service';
import {Movie} from '../models/movie.model';
import {Celebrity} from '../models/celebrity.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss'],
  providers: [SearchService]
})
export class SearchComponent implements OnInit {
  movieResults:Movie[];
  movieCount: any;
  keywordParam: String;
  celebritiesResults: Celebrity[];
  celebrityCount: any;
  searchPeopleBoolean = false;
  searchMovieBoolean = true;

  constructor(private route: ActivatedRoute, private searchService: SearchService, private router: Router) {
  }

  ngOnInit() {
    this.route.queryParamMap.subscribe((params: ParamMap)=>{
      this.keywordParam = params.get('keyword');
      if(this.searchPeopleBoolean){
        this.searchForCelebrities();
      }
      else if(this.searchMovieBoolean){
        this.searchForMovies();
      }
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
  } //end onInit

  searchForMovies(){
    if(!(this.keywordParam.trim()  == "")){
      // search only if input is more than two letters
      if(this.keywordParam.trim().length < 2){this.keywordParam =null;}
      this.searchService.searchMovies(this.keywordParam)
        .subscribe(
          data => {
            // console.log(data);
            this.movieResults = data as Movie[];
            this.movieCount=this.movieResults.length;
          },
          error => console.log('Failed to fetch movie data')
        );
    }
  }

  searchForCelebrities(){
    if(!(this.keywordParam.trim()  == "")){
      // search only if input is more than two letters
      if(this.keywordParam.trim().length < 2){this.keywordParam =null;}
      this.searchService.searchCelebrities(this.keywordParam)
        .subscribe(
          data => {
            // console.log(data);
            this.celebritiesResults = data as Celebrity[];
            this.celebrityCount=this.celebritiesResults.length;
          },
          error => console.log('Failed to fetch celebrity data')
        );
    }
  }

  setSearchPeopleTrue(){
    this.searchPeopleBoolean = true;
    this.searchMovieBoolean = false;
    this.route.queryParamMap.subscribe((params: ParamMap)=>{
      this.keywordParam = params.get('keyword');
      this.searchForCelebrities();
    });
  }

  setSearchMovieTrue(){
    this.searchPeopleBoolean = false;
    this.searchMovieBoolean = true;
    this.route.queryParamMap.subscribe((params: ParamMap)=>{
      this.keywordParam = params.get('keyword');
      this.searchForMovies();
    });
  }

  //TODO TV search
}
