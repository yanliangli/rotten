import {Component, OnInit} from '@angular/core';
import {HomeService} from '../../home/home.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {SearchService} from '../search.service';
import {Movie} from '../../models/movie.model';
import {Page} from 'ngx-pagination/dist/pagination-controls.directive';

@Component({
  selector: 'search-tv-tab',
  templateUrl: './tv-tab.component.html',
  styleUrls: ['../search.component.scss'],
})
export class SearchTVTabComponent implements OnInit{
  keywordParam;
  tvSearchCount=0;
  tvResults: Movie[];
  itemsPerPage=7;
  maxPageDisplay=7;
  tvPage:number = 1;
  tvLineCount;
  constructor(private homeService: HomeService,private route: ActivatedRoute, private router: Router, private searchService: SearchService){}
  ngOnInit() {
    this.route.queryParamMap.subscribe((params: ParamMap)=>{
      this.keywordParam = params.get('keyword');
      this.searchForTV();
    });
  }
  searchForTV(){
    this.searchService.searchTV(this.keywordParam, this.tvPage, this.itemsPerPage)
      .subscribe(
        data=>{
          if(data){
            console.log(data)
            this.tvResults = (data as Page[])['content'];
            this.tvSearchCount = (data as Page[])['totalElements'];
            this.tvLineCount = this.tvResults.length;
          }
        },
        error => console.log('Failed to fetch celebrity data')
      );
  }

}
