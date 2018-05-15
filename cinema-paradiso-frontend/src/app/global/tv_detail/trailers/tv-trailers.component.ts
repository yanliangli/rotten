import {Component, OnInit} from '@angular/core';
import {Movie} from '../../models/movie.model';
import {TvDetailService} from '../tv-detail.service';
import {ActivatedRoute} from '@angular/router';
import {DomSanitizer} from '@angular/platform-browser';

@Component({
  selector: 'search-tv-trailers',
  templateUrl: './tv-trailers.component.html',
  styleUrls: ['../tv-detail.component.scss'],
})
export class TvTrailersComponent implements OnInit{
  selectedMovieId: string;
  movie: Movie;
  myTrailers:string[] = [];
  constructor(private route: ActivatedRoute, private tvDetailService: TvDetailService, private sanitizer: DomSanitizer){
  }

  ngOnInit(){
    this.selectedMovieId = this.route.snapshot.params['id'];
    this.getMovie(this.selectedMovieId);
  }

  getMovie(imdbId: string): any {
    console.log('Selected movie: ' + imdbId);
    this.tvDetailService.getMovieDetails(imdbId)
      .subscribe(
        data => {
          console.log(data);
          this.movie = data as Movie;
          this.getTrailerData(this.movie.trailers);
        },
        error => console.log('Failed to fetch movie with id')
      );

  }
  getTrailerData(trailerIds : string[]){
    let prefix = "https://www.imdb.com/video/imdb/";
    let postfix = "/imdb/embed?autoplay=false&width=400";

    let i;
    for(i=0;i<trailerIds.length;i++) {
      let x = prefix+trailerIds[i]+postfix;
      this.myTrailers.push(x);
    }
  }
  trailerURL(str:string){
    return this.sanitizer.bypassSecurityTrustResourceUrl(str);
  }
}
