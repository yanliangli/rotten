import {Component, OnInit} from '@angular/core';
import {Movie} from '../../models/movie.model';
import {MovieDetailService} from '../movie-detail.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'search-movie-reviews',
  templateUrl: './movie-reviews.component.html',
  styleUrls: ['../movie-detail.component.scss'],
})
export class MovieReviewsComponent implements OnInit{
  selectedMovieId: string;
  movie: Movie;
  constructor(private route: ActivatedRoute, private movieDetailService:MovieDetailService){
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
        },
        error => console.log('Failed to fetch movie with id')
      );

  }
}
