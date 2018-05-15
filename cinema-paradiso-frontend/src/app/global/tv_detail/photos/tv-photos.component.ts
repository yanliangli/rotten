import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Movie} from '../../models/movie.model';
import {TvDetailService} from '../tv-detail.service';

@Component({
  selector: 'search-tv-photos',
  templateUrl: './tv-photos.component.html',
  styleUrls: ['../tv-detail.component.scss'],
})
export class TvPhotosComponent implements OnInit{
  selectedMovieId: string;
  movie: Movie;
  constructor(private route: ActivatedRoute, private tvDetailService: TvDetailService){
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
        },
        error => console.log('Failed to fetch movie with id')
      );

  }
}
