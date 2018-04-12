package com.paridiso.cinema.service;

import com.paridiso.cinema.entity.Film;
import com.paridiso.cinema.entity.Movie;
import com.paridiso.cinema.entity.Trailer;

import java.util.Date;
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

    Set<Trailer> getTrailers(String filmId);

    boolean updateTrailer(String filmId, Integer trailerId);

    String getPoster(String filmId);

    boolean updatePoster(String filmId, String poster);

    List<Movie> getMoviesInRage(Date startDate, Date endDate);

    List<Movie> getSimilarMovies(String filmId);

    List<Movie> getTrending();

    List<Movie> getMoviesPlaying();

    List<Movie> getTopRating();

    List<Movie> getTopBoxOffice();


}
