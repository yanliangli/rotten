package com.paridiso.cinema.persistence;

import com.paridiso.cinema.entity.Film;
import com.paridiso.cinema.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// JpaRepo can't be <Film, String> because Film is not an entity
@Repository
public interface MovieRepository extends JpaRepository<Movie, String>{

    Movie findMovieByImdbId(String filmImdbId);

    Movie findMovieByTitle(String filmTitle);

    List<Movie> findMoviesByTitle(String filmTitle);

    List<Movie> findMoviesByTitleContains(String filmTitle);

//    List<Movie> findMoviesBy

}
