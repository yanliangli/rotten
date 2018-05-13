import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {SearchService} from '../search.service';
import {Page} from 'ngx-pagination/dist/pagination-controls.directive';

@Component({
  selector: 'search-toolbar',
  templateUrl: './search-toolbar.component.html',
  styleUrls: ['../search.component.scss'],
  providers: [SearchService]
})
export class SearchToolbarComponent implements OnInit{
  toolbar_keyword;
  movieCount=0;
  celebrityCount=0;
  tvCount=0;
  itemsPerPage=7;
  page:number = 1;

  constructor(private route: ActivatedRoute, private searchService: SearchService, private router: Router) {
  }

  ngOnInit() {
    this.route.queryParamMap.subscribe((params: ParamMap)=>{
      this.toolbar_keyword = params.get('keyword');
      this.getCount();
    });
  } //end onInit

  getCount(){
    this.searchForMovies();
    this.searchForCelebrities();
    this.searchForTV();
  }
  searchForMovies(){
    this.searchService.searchMovies(this.toolbar_keyword, this.page, this.itemsPerPage)
      .subscribe(
        data=>{
          if(data){
            console.log(data)
            this.movieCount=(data as Page[])['totalElements'];
          }
        },
        error => console.log('Failed to fetch movie data')
      );
  }

  searchForCelebrities(){
    this.searchService.searchCelebrities(this.toolbar_keyword, this.page, this.itemsPerPage)
      .subscribe(
        data=>{
          if(data){
            console.log(data)
            this.celebrityCount = (data as Page[])['totalElements'];
          }
        },
        error => console.log('Failed to fetch celebrity data')
      );
  }

  searchForTV(){
    this.searchService.searchTV(this.toolbar_keyword, this.page, this.itemsPerPage)
      .subscribe(
        data=>{
          if(data){
            console.log(data)
            this.tvCount = (data as Page[])['totalElements'];
          }
        },
        error => console.log('Failed to fetch tv data')
      );
  }
}
