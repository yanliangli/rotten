import {Component, OnInit} from '@angular/core';
import {Movie} from '../../models/movie.model';
import {TvDetailService} from '../tv-detail.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'search-tv-reviews',
  templateUrl: './tv-reviews.component.html',
  styleUrls: ['../tv-detail.component.scss'],
})
export class TvReviewsComponent implements OnInit{
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
