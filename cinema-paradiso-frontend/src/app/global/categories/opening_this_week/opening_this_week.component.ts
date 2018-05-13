import {Component, OnInit} from '@angular/core';
import {HomeService} from '../../home/home.service';
import {Page} from 'ngx-pagination/dist/pagination-controls.directive';
import {Movie} from '../../models/movie.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'category-opening-this-week',
  templateUrl: './opening_this_week.component.html',
  styleUrls: ['../category.scss'],
})
export class OpeningThisWeekComponent implements OnInit{
  moviesOpening:Movie[];
  movieCount: number=0;
  page:number = 1;
  numberOfElements=20;
  maxPageDisplay=7;
  sortBy;
  orderBy="ASC";
  constructor(private homeService: HomeService,private route: ActivatedRoute, private router: Router){}
  ngOnInit(){
    this.route.queryParamMap.subscribe((params: ParamMap)=> {
      if(params.get('page')){
        this.page= Number(params.get('page'));
      }
      this.sortBy = params.get('sortBy');
      if(this.sortBy==null){
        this.sortBy="";
      }
      if(this.sortBy=="rating" || this.sortBy=="numberOfRatings"){
        this.orderBy="DESC";
      }
      else{
        this.orderBy="ASC";
      }
      this.getAllOpeningThisWeek(this.sortBy, this.orderBy);
    });
  }
  getAllOpeningThisWeek(sortBy, orderBy){
    this.homeService.getMoviesOpeningThisWeek(this.page,this.numberOfElements, sortBy, orderBy)
      .subscribe(
        data => {
          this.moviesOpening = (data as Page[])['content'];
          this.movieCount=(data as Page[])['totalElements'];
          console.log(data);
        },
        error => console.log('Failed to fetch movies opening this week')
      );
  }
  convertStringToDate(d:Date){
    let date:Date;
    date = new Date(d.toString());
    return date.toLocaleDateString("en-En", {month:'short', day:'numeric'});
  }

  onPageChange(page, itemsPerPage, sort, order){
    this.router.navigate(['/movies/opening_this_week'], {queryParams: {page:page, itemsPerPage:itemsPerPage, sortBy:sort, oder:order}});
  }
}
