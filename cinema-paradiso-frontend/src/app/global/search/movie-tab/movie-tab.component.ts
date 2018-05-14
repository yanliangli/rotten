import {Component, OnInit} from '@angular/core';
import {HomeService} from '../../home/home.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {Movie} from '../../models/movie.model';
import {Page} from 'ngx-pagination/dist/pagination-controls.directive';
import {SearchService} from '../search.service';

@Component({
  selector: 'search-movie-tab',
  templateUrl: './movie-tab.component.html',
  styleUrls: ['../search.component.scss'],
})
export class SearchMovieTabComponent implements OnInit{
  keywordParam;
  movieSearchCount=0;
  movieResults: Movie[];
  itemsPerPage=7;
  maxPageDisplay=7;
  moviePage:number = 1;
  movieLineCount;
  sortBy;
  orderBy;
  constructor(private homeService: HomeService,private route: ActivatedRoute, private router: Router, private searchService: SearchService){}
  ngOnInit() {
    this.route.queryParamMap.subscribe((params: ParamMap)=>{
      this.keywordParam = params.get('keyword');
      if(params.get('page')){
        this.moviePage= Number(params.get('page'));
      }
      this.sortBy = params.get('sortBy');
      if(this.sortBy==null){
        this.sortBy="";
      }
      this.orderBy = params.get('order');
      if(!this.orderBy){
        this.orderBy="";
      }
      this.searchForMovies();
    });
  }
  searchForMovies(){
    this.searchService.searchMovies(this.keywordParam, this.moviePage, this.itemsPerPage, this.sortBy, this.orderBy)
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

  onPageChange(page, itemsPerPage, sort, order){
    this.router.navigate(['/search/movie'], {queryParams: {keyword:this.keywordParam, page:page, itemsPerPage:itemsPerPage, sortBy:sort, oder:order}});
  }
}
