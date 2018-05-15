import {Component, OnInit} from '@angular/core';
import {Movie} from '../../models/movie.model';
import {MovieDetailService} from '../movie-detail.service';
import {ActivatedRoute} from '@angular/router';
import {DomSanitizer} from '@angular/platform-browser';

@Component({
  selector: 'search-movie-trailers',
  templateUrl: './movie-trailers.component.html',
  styleUrls: ['../movie-detail.component.scss'],
})
export class MovieTrailersComponent implements OnInit{
  selectedMovieId: string;
  movie: Movie;
  myTrailers:string[] = [];
  constructor(private route: ActivatedRoute, private movieDetailService:MovieDetailService, private sanitizer: DomSanitizer){
  }

  ngOnInit(){
    this.selectedMovieId = this.route.snapshot.params['id'];
    this.getMovie(this.selectedMovieId);
  }

  getMovie(imdbId: string): any {
    console.log('Selected movie: ' + imdbId);
    this.movieDetailService.getMovieDetails(imdbId)
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
