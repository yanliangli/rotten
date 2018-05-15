import {Component, OnInit} from '@angular/core';
import {HomeService} from '../../home/home.service';
import {Page} from 'ngx-pagination/dist/pagination-controls.directive';
import {Movie} from '../../models/movie.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'category-certified-fresh_movie',
  templateUrl: './certified_fresh_movies.component.html',
  styleUrls: ['../category.scss'],
})
export class CertifiedFreshMovieComponent implements OnInit{
  certifiedFreshMovie:Movie[];
  movieCount: number=0;
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
      this.getMoviesCertifiedFresh(this.sortBy, this.orderBy);
    });
  }
  getMoviesCertifiedFresh(sortBy, orderBy){
    this.homeService.getMoviesCertifiedFresh(this.page,this.numberOfElements, sortBy, orderBy)
      .subscribe(
        data => {
          this.certifiedFreshMovie = (data as Page[])['content'];
          this.movieCount=(data as Page[])['totalElements'];
          console.log(data);
        },
        error => console.log('Failed to fetch movies opening this week')
      );
  }

  numberInMillion(labelValue:any) {
    if(labelValue == null){
      return "N/A";
    }
    // Nine Zeroes for Billions
    return Math.abs(Number(labelValue)) >= 1.0e+9 ? (Math.abs(Number(labelValue)) / 1.0e+9).toFixed(2) + "B"
      // Six Zeroes for Millions
      : Math.abs(Number(labelValue)) >= 1.0e+6 ? (Math.abs(Number(labelValue)) / 1.0e+6).toFixed(2) + "M"
        // Three Zeroes for Thousands
        : Math.abs(Number(labelValue)) >= 1.0e+3 ? (Math.abs(Number(labelValue)) / 1.0e+3).toFixed(2) + "K"
          : Math.abs(Number(labelValue));
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
    this.router.navigate(['/movies/certified_fresh_movie'], {queryParams: {page:page, itemsPerPage:itemsPerPage, sortBy:sort, oder:order}});
  }
}
