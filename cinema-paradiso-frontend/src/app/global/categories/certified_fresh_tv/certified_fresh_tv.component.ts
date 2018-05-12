import {Component, OnInit} from '@angular/core';
import {HomeService} from '../../home/home.service';
import {Page} from 'ngx-pagination/dist/pagination-controls.directive';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {TV} from '../../models/tv.model';

@Component({
  selector: 'category-certified-fresh_tv',
  templateUrl: './certified_fresh_tv.component.html',
  styleUrls: ['../category.scss'],
})
export class CertifiedFreshTVComponent implements OnInit{
  certifiedFreshTV:TV[];
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
      this.getTVCertifiedFresh();
    });
  }
  getTVCertifiedFresh(){
    this.homeService.getTVCertifiedFresh(this.page,this.numberOfElements)
      .subscribe(
        data => {
          this.certifiedFreshTV = (data as Page[])['content'];
          this.tvCount=(data as Page[])['totalElements'];
          console.log(data);
        },
        error => console.log('Failed to fetch certified fresh tv')
      );
  }

  onPageChange(page, itemsPerPage){
    this.router.navigate(['/movies/certified_fresh_tv'], {queryParams: {page:page, itemsPerPage:itemsPerPage}});
  }
}
