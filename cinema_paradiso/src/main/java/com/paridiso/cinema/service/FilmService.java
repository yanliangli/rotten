package com.paridiso.cinema.service;

import com.paridiso.cinema.entity.Film;
import com.paridiso.cinema.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FilmService {

//    Film addFilm(Film film);
    Optional<Movie> addMovie(Movie movie);

    Film getFilm(String filmId);

    List<Movie> getMovies();

    Movie updateMovie(Movie movie);

    List<Movie> getCarouselMovies();

    void deleteFilm(String filmId);

    void rateFilm(String filmId, Double rating);

    Set<String> getTrailers(String filmId);

    boolean updateTrailer(String filmId, Integer trailerId);

    String getPoster(String filmId);

    boolean updatePoster(String filmId, String poster);

    List<Movie> getMoviesInRage(String startDate, String endDate);

    List<Movie> getSimilarMovies(String filmId);

    List<Movie> getTrending();

    Page<Movie> getTopRating(Pageable pageable);

    Page<Movie> getTopBoxOffice(Pageable pageable);

    Page<Movie> getMoviesOpeningThisWeek(Pageable pageable);

    Page<Movie> getInTheatersNow(Pageable pageable);

    Page<Movie> getMoviesComingSoon(Pageable pageable);

    Page<Movie> getCertifiedFresh(Pageable pageable);

    Page<Movie> getOscarBestPictures(Pageable pageable);


    Sort getSortParam(String order, String sort);





}
