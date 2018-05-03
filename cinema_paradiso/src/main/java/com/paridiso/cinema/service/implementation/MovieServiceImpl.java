package com.paridiso.cinema.service.implementation;

import com.paridiso.cinema.entity.Film;
import com.paridiso.cinema.entity.Movie;
import com.paridiso.cinema.entity.Trailer;
import com.paridiso.cinema.persistence.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.paridiso.cinema.service.FilmService;
import com.paridiso.cinema.service.UtilityService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
@Qualifier(value = "MovieServiceImpl")
public class MovieServiceImpl implements FilmService {

    @Autowired
    MovieRepository movieRepository;

    @Override
    public Optional<Movie> addMovie(Movie movie) {
        if (movieRepository.findMovieByImdbId(movie.getImdbId()) != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MOVIE EXISTS");
        return Optional.ofNullable(movieRepository.save(movie));
    }

    @Transactional
    @Override
    public Film getFilm(String filmId) {
        return movieRepository.findMovieByImdbId(filmId);
    }

    @Transactional
    @Override
    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    // TODO: movies
    @Override
    public List<Movie> getCarouselMovies() {
        List<Movie> movieList = new ArrayList<>();
        Movie movie1 = (Movie) this.getFilm("tt2380307");
        Movie movie2 = (Movie) this.getFilm("tt5052448");
        Movie movie3 = (Movie) this.getFilm("tt1856101");
        movieList.addAll(Arrays.asList(movie1, movie2, movie3));
        return movieList;
    }

    @Transactional
    @Override
    public Movie updateMovie(Movie movie) {
        if (movieRepository.findMovieByImdbId(movie.getImdbId()) == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MOVIE DOES NOT EXIST");
        return movieRepository.save(movie);
    }

    @Transactional
    @Override
    public void deleteFilm(String filmId) {
        if (movieRepository.findMovieByImdbId(filmId) == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MOVIE DOES NOT EXIST");
        movieRepository.deleteById(filmId);
    }

    @Transactional
    @Override
    public void rateFilm(String filmId, Double rating) {
        // add the rating to total rating, then get average
        Movie movie = (Movie) this.getFilm(filmId);
        if (movie.getNumberOfRatings() == null) {
            movie.setNumberOfRatings(10+1);
        } else {
            movie.setNumberOfRatings(movie.getNumberOfRatings() + 1);
        }
        Double newRatings = (double)Math.round(((movie.getRating()*(movie.getNumberOfRatings()-1) + rating) / movie.getNumberOfRatings())*10)/10;
        movie.setRating(newRatings);
        movieRepository.save(movie);
    }

    @Override
    public Set<Trailer> getTrailers(String filmId) {
        return this.getFilm(filmId).getTrailers();
    }

    @Override
    public boolean updateTrailer(String filmId, Integer trailerId) {
        return false;
    }

    @Override
    public String getPoster(String filmId) {
        return this.getFilm(filmId).getPoster();
    }

    @Override
    public boolean updatePoster(String filmId, String poster) {
        this.getFilm(filmId).setPoster(poster);
        return true;
    }

    @Override
    public List<Movie> getMoviesInRage(String startDate, String endDate) {
        return null;
    }

    @Override
    public List<Movie> getSimilarMovies(String filmId) {
        return null;
    }



    // TODO: find proper movies
    @Transactional
    @Override
    public List<Movie> getMoviesOpeningThisWeek() {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date dateNow = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateNow);
        cal.add(Calendar.DATE, -3);
        Date dateBefore = cal.getTime();
        cal.add(Calendar.DATE, 7);
        Date dateAfter = cal.getTime();
        return movieRepository.findAllByReleaseDateBetween(dateBefore, dateAfter);
    }

    @Override
    public List<Movie> getMoviesComingSoon() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date dateNow = new Date();
        return movieRepository.findAllByReleaseDateAfter(dateNow);
    }

    @Override
    public List<Movie> getTopRating() {
        return movieRepository.findTop6ByOrderByRatingDesc();
    }

    @Override
    public List<Movie> getTopBoxOffice() {
        return movieRepository.findTop60ByOrderByBoxOfficeDesc();
    }

    @Override
    public List<Movie> getTrending() {
        return movieRepository.findTop6ByOrderByNumberOfRatingsDesc();
    }


}
