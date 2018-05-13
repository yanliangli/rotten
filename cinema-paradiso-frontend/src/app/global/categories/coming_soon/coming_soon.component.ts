
import {Component, OnInit} from '@angular/core';
import {HomeService} from '../../home/home.service';
import {Page} from 'ngx-pagination/dist/pagination-controls.directive';
import {Movie} from '../../models/movie.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'category-coming-soon-component',
  templateUrl: './coming_soon.component.html',
  styleUrls: ['../category.scss'],
})
export class ComingSoonComponent implements OnInit{
  moviesComingSoon:Movie[];
  movieCount: number=0;
  page:number = 1;
  numberOfElements=20;
  maxPageDisplay=7;
  sortBy;
  orderBy="ASC";
  constructor(private homeService: HomeService,private route: ActivatedRoute,  private router: Router){}
  ngOnInit() {
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
      this.getAllComingSoon(this.sortBy, this.orderBy);
    });
  }
  getAllComingSoon(sortBy, orderBy){
    this.homeService.getMoviesComingSoon(this.page,this.numberOfElements, sortBy, orderBy)
      .subscribe(
        data => {
          this.moviesComingSoon = (data as Page[])['content'];
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
    this.router.navigate(['/movies/coming_soon'], {queryParams: {page:page, itemsPerPage:itemsPerPage, sortBy:sort, oder:order}});
  }
}
