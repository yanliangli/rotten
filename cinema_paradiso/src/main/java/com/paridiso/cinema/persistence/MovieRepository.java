package com.paridiso.cinema.persistence;

import com.paridiso.cinema.entity.Film;
import com.paridiso.cinema.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

// JpaRepo can't be <Film, String> because Film is not an entity
@Repository
public interface MovieRepository extends JpaRepository<Movie, String>{

    Movie findMovieByImdbId(String filmImdbId);

    Movie findMovieByTitle(String filmTitle);

    List<Movie> findMoviesByTitle(String filmTitle);

    List<Movie> findMoviesByTitleContains(String filmTitle);

    // sort movies by rating
    List<Movie> findAllByOrderByRatingAsc();
    List<Movie> findAllByOrderByRatingDesc();

    // sort movies by date
    List<Movie> findAllByOrderByReleaseDateAsc();
    List<Movie> findAllByOrderByReleaseDateDesc();

    // get top 6 box office movies
    List<Movie> findTop6ByOrderByBoxOfficeDesc();



    // get trending movies
    List<Movie> findTop6ByOrderByNumberOfRatingsDesc();

    // get top rating movies
    List<Movie> findTop6ByOrderByRatingDesc();

    // get now playing movies
    List<Movie> findAllByReleaseDateBetween(Date date1, Date date2);

    // get coming soon movies
    List<Movie> findAllByReleaseDateAfter(Date date1);

    // get top 60 rated movies
    List<Movie> findTop60ByOrderByRatingDesc();

    // get top 60 box office movies
    List<Movie> findTop60ByOrderByBoxOfficeDesc();

    //    List<Movie> findMoviesBy

}
