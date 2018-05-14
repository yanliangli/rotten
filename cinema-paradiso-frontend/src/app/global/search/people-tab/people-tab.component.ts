import {Component, OnInit} from '@angular/core';
import {HomeService} from '../../home/home.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {SearchService} from '../search.service';
import {Movie} from '../../models/movie.model';
import {Page} from 'ngx-pagination/dist/pagination-controls.directive';

@Component({
  selector: 'search-people-tab',
  templateUrl: './people-tab.component.html',
  styleUrls: ['../search.component.scss'],
})
export class SearchPeopleTabComponent implements OnInit{
  keywordParam;
  celebritySearchCount=0;
  celebritiesResults: Movie[];
  itemsPerPage=7;
  maxPageDisplay=7;
  celebrityPage:number = 1;
  celebrityLineCount;
  sortBy;
  orderBy;
  constructor(private homeService: HomeService,private route: ActivatedRoute, private router: Router, private searchService: SearchService){}
  ngOnInit() {
    this.route.queryParamMap.subscribe((params: ParamMap)=>{
      this.keywordParam = params.get('keyword');
      if(params.get('page')){
        this.celebrityPage= Number(params.get('page'));
      }
      this.sortBy = params.get('sortBy');
      if(this.sortBy==null){
        this.sortBy="";
      }
      this.orderBy = params.get('order');
      if(!this.orderBy){
        this.orderBy="";
      }
      this.searchForCelebrities();
    });
  }
  searchForCelebrities(){
    this.searchService.searchCelebrities(this.keywordParam, this.celebrityPage, this.itemsPerPage, this.sortBy, this.orderBy)
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
  onPageChange(page, itemsPerPage, sort, order){
    this.router.navigate(['/search/celebrity'], {queryParams: {keyword:this.keywordParam, page:page, itemsPerPage:itemsPerPage, sortBy:sort, oder:order}});
  }
}
