package com.paridiso.cinema.persistence;

import com.paridiso.cinema.entity.Film;
import com.paridiso.cinema.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    //List<Movie> findMoviesByTitleContains(String filmTitle);
    Page<Movie> findMoviesByTitleContains(String keyword, Pageable pageable);

    // sort movies by rating
//    List<Movie> findAllByOrderByRatingAsc();
//    List<Movie> findAllByOrderByRatingDesc();
//
//    // sort movies by date
//    List<Movie> findAllByOrderByReleaseDateAsc();
//    List<Movie> findAllByOrderByReleaseDateDesc();
//
//    // get top 6 box office movies
//    List<Movie> findTop6ByOrderByBoxOfficeDesc();



    // get trending movies
    List<Movie> findTop6ByOrderByNumberOfRatingsDesc();

    // get top rating movies
    List<Movie> findTop6ByOrderByRatingDesc();

    // get now playing movies
    Page<Movie> findAllByReleaseDateBetween(Date date1, Date date2, Pageable pageable);

    // get coming soon movies
    Page<Movie> findAllByReleaseDateAfter(Date date1, Pageable pageable);

    // get top 60 rated movies
    Page<Movie> findAllByOrderByRatingDesc(Pageable pageable);

    // get top 60 box office movies
    Page<Movie> findAllByOrderByBoxOfficeDesc(Pageable pageable);


    //    List<Movie> findMoviesBy

}
