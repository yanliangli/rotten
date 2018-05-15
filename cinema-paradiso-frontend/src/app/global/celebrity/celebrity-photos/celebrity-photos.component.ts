import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {CelebrityService} from '../celebrity.service';
import {Celebrity} from '../../models/celebrity.model';

@Component({
  selector: 'app-celebrity-photos',
  templateUrl: './celebrity-photos.component.html',
  styleUrls: ['../../movie-detail/movie-detail.component.scss', '../celebrity.component.scss']
})
export class CelebrityPhotosComponent implements OnInit {
  selectedCelebrityId:string;
  celebrity:Celebrity;
  constructor(private route: ActivatedRoute, private celebrityService:CelebrityService) { }

  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap)=>{
      this.selectedCelebrityId = params.get('id')
      this.getCelebrity(this.selectedCelebrityId);
    });
  }

  getCelebrity(imdbId:string){
    this.celebrityService.getCelebrity(imdbId).subscribe(
      data=>{
        if(data){
          this.celebrity = data as Celebrity;
          console.log(this.celebrity);
        }
      },
      error => console.log('Failed to fetch celebrity data')
    );
  }
}
