import {Component, OnInit} from '@angular/core';
import {HomeService} from '../../home/home.service';
import {Page} from 'ngx-pagination/dist/pagination-controls.directive';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {TV} from '../../models/tv.model';

@Component({
  selector: 'category-new-tv-tonight',
  templateUrl: './new_tv_tonight.component.html',
  styleUrls: ['../category.scss'],
})
export class NewTVTonightComponent implements OnInit{
  newTVTonight:TV[];
  tvCount: number=0;
  page:number = 1;
  numberOfElements=20;
  maxPageDisplay=7;
  constructor(private homeService: HomeService,private route: ActivatedRoute, private router: Router){}
  ngOnInit() {
    this.route.queryParamMap.subscribe((params: ParamMap)=> {
      if(params.get('page')){
        this.page= Number(params.get('page'));
      }
      this.getNewTVTonight();
    });
  }
  getNewTVTonight(){
    this.homeService.getNewTVTonight(this.page,this.numberOfElements)
      .subscribe(
        data => {
          this.newTVTonight = (data as Page[])['content'];
          this.tvCount=(data as Page[])['totalElements'];
          console.log(data);
        },
        error => console.log('Failed to fetch new tv tonight')
      );
  }

  onPageChange(page, itemsPerPage){
    this.router.navigate(['/movies/new_tv_tonight'], {queryParams: {page:page, itemsPerPage:itemsPerPage}});
  }
}
