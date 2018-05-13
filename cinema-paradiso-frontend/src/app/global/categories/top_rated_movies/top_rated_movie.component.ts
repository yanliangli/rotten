import {Component, OnInit} from '@angular/core';
import {HomeService} from '../../home/home.service';
import {Page} from 'ngx-pagination/dist/pagination-controls.directive';
import {Movie} from '../../models/movie.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'category-top-rated-movie',
  templateUrl: './top_rated_movie.component.html',
  styleUrls: ['../category.scss'],
})
export class TopRatedMovieComponent implements OnInit{
  topRatedMovie:Movie[];
  movieCount: number = 100;
  page:number = 1;
  numberOfElements=20;
  maxPageDisplay=7;
  sortBy;
  orderBy;
  constructor(private homeService: HomeService,private route: ActivatedRoute, private router: Router){}
  ngOnInit() {
    this.route.queryParamMap.subscribe((params: ParamMap)=> {
      if(params.get('page')){
        this.page= Number(params.get('page'));
      }
      this.sortBy = params.get('sortBy');
      if(this.sortBy==null){
        this.sortBy="";
      }
      this.orderBy = params.get('order');
      if(!this.orderBy){
        this.orderBy="";
      }
      this.getTopRating(this.sortBy, this.orderBy);
    });
  }
  getTopRating(sortBy, orderBy){
    this.homeService.getTopRatedMovie(this.page,this.numberOfElements, sortBy, orderBy)
      .subscribe(
        data => {
          this.topRatedMovie = (data as Page[])['content'];
          console.log(data);
        },
        error => console.log('Failed to fetch movies opening this week')
      );
  }

  convertStringToDate(d:Date){
    if(d==null){
      return "N/A";
    }
    let date:Date;
    date = new Date(d.toString());
    return date.toLocaleDateString("en-En", {month:'short', day:'numeric'});
  }

  onPageChange(page, itemsPerPage, sort, order){
    this.router.navigate(['/movies/top_rated_movie'], {queryParams: {page:page, itemsPerPage:itemsPerPage, sortBy:sort, oder:order}});
  }
}
