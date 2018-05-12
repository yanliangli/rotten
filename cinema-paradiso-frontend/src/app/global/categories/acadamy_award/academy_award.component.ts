import {Component, OnInit} from '@angular/core';
import {HomeService} from '../../home/home.service';
import {Page} from 'ngx-pagination/dist/pagination-controls.directive';
import {Movie} from '../../models/movie.model';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'category-academy-award',
  templateUrl: './academy_award.component.html',
  styleUrls: ['../category.scss'],
})
export class AcademyAwardComponent implements OnInit{
  academyAward:Movie[];
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
      this.getAllAcademyAwards(this.sortBy, this.orderBy);
    });
  }
  getAllAcademyAwards(sortBy, orderBy){
    this.homeService.getAcademyAward(this.page,this.numberOfElements, sortBy, orderBy)
      .subscribe(
        data => {
          this.academyAward = (data as Page[])['content'];
          this.movieCount=(data as Page[])['totalElements'];
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
    this.router.navigate(['/movies/academy_award'], {queryParams: {page:page, itemsPerPage:itemsPerPage, sortBy:sort, oder:order}});
  }
}
