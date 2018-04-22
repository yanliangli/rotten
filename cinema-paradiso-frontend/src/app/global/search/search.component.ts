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
  tableParam: String;
  keywordParam: String;
  celebritiesResults: Celebrity[];

  constructor(private route: ActivatedRoute, private searchService: SearchService, private router: Router) {
  }

  ngOnInit() {
    this.route.queryParamMap.subscribe((params: ParamMap)=>{
      this.tableParam = params.get('table');
      this.keywordParam = params.get('keyword');
      this.searchForMovies();
      console.log(this.tableParam, this.keywordParam);
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
            console.log(this.movieResults);
          },
          error => console.log('Failed to fetch movie data')
        );
    }
  }

  searchForCelebrities(){

  }
}
