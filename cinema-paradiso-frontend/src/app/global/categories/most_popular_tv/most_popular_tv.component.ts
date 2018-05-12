import {Component, OnInit} from '@angular/core';
import {HomeService} from '../../home/home.service';
import {Page} from 'ngx-pagination/dist/pagination-controls.directive';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {TV} from '../../models/tv.model';

@Component({
  selector: 'category-most-popular-tv',
  templateUrl: './most_popular_tv.component.html',
  styleUrls: ['../category.scss'],
})
export class MostPopularTVComponent implements OnInit{
  mostPopular:TV[];
  tvCount: number=100;
  page:number = 1;
  numberOfElements=20;
  maxPageDisplay=7;
  constructor(private homeService: HomeService,private route: ActivatedRoute, private router: Router){}
  ngOnInit() {
    this.route.queryParamMap.subscribe((params: ParamMap)=> {
      if(params.get('page')){
        this.page= Number(params.get('page'));
      }
      this.getMostPopularTV();
    });
  }
  getMostPopularTV(){
    this.homeService.getMostPopularTVOnCP(this.page,this.numberOfElements)
      .subscribe(
        data => {
          this.mostPopular = (data as Page[])['content'];
          console.log(data);
        },
        error => console.log('Failed to fetch most popular tv')
      );
  }

  onPageChange(page, itemsPerPage){
    this.router.navigate(['/movies/most_popular_tv'], {queryParams: {page:page, itemsPerPage:itemsPerPage}});
  }
}
