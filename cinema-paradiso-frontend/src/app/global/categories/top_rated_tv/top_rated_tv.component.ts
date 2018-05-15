import {Component, OnInit} from '@angular/core';
import {HomeService} from '../../home/home.service';
import {Page} from 'ngx-pagination/dist/pagination-controls.directive';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {TV} from '../../models/tv.model';

@Component({
  selector: 'category-top-rated-tv',
  templateUrl: './top_rated_tv.component.html',
  styleUrls: ['../category.scss'],
})
export class TopRatedTVComponent implements OnInit{
  topRatedTv:TV[];
  constructor(private homeService: HomeService,private route: ActivatedRoute, private router: Router){}
  ngOnInit() {
    this.route.queryParamMap.subscribe((params: ParamMap)=> {
      this.getTopRatingTV();
    });
  }
  getTopRatingTV(){
    this.homeService.getTopRatedTV(1, 100)
      .subscribe(
        data => {
          this.topRatedTv = (data as Page[])['content'];
          console.log(data);
        },
        error => console.log('Failed to fetch top rating tv')
      );
  }

  onPageChange(page, itemsPerPage){
    this.router.navigate(['/movies/top_rated_tv'], {queryParams: {page:page, itemsPerPage:itemsPerPage}});
  }
}
