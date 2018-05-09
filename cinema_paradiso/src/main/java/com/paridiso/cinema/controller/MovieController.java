package com.paridiso.cinema.controller;

import com.paridiso.cinema.entity.Movie;
import com.paridiso.cinema.service.JwtTokenService;
import com.paridiso.cinema.service.UserService;
import com.paridiso.cinema.service.implementation.RegUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.paridiso.cinema.service.FilmService;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.web.bind.annotation.RequestMethod.*;


@RequestMapping("/movie")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MovieController {

    @Autowired
    @Qualifier("MovieServiceImpl")
    FilmService filmService;

    @Autowired
    JwtTokenService jwtTokenService;

    @Autowired
    RegUserServiceImpl userService;

    @RequestMapping(value = "/all", method = GET)
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(filmService.getMovies());
    }

    @RequestMapping(value = "/carousel", method = GET)
    public ResponseEntity<List<Movie>> getCarousel() {
        return ResponseEntity.ok(filmService.getCarouselMovies());
    }

    @RequestMapping(value = "/{filmId}", method = GET)
    public ResponseEntity<Movie> getMovie(@PathVariable String filmId) {
        Movie movie = (Movie) filmService.getFilm(filmId);
        if (movie != null)
            return new ResponseEntity<>(movie, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/add", method = POST)
    public ResponseEntity<Boolean> addMovie(@RequestBody Movie movie) {
        Movie optionalMovie = filmService.addMovie(movie).orElseThrow(() ->
                new ResponseStatusException(BAD_REQUEST, "Unable to add movie"));
        return ResponseEntity.ok(true);
    }


    @RequestMapping(value = "/delete/{filmId}", method = DELETE)
    public ResponseEntity<Boolean> deleteMovie(@PathVariable String filmId) {
        filmService.deleteFilm(filmId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/{filmId}/{rating}", method = POST)
    public ResponseEntity<Boolean> rateMovie(@RequestHeader(value = "Authorization") String jwtToken,
                                             @PathVariable String filmId,
                                             @PathVariable Double rating) {
        // add to user
        boolean result = userService.rateMovie(jwtTokenService.getUserIdFromToken(jwtToken), filmId, rating);
        if (!result)
            return ResponseEntity.ok(false);

        // add to film
        filmService.rateFilm(filmId, rating);
        return ResponseEntity.ok(true);
    }


    @DeleteMapping(value = "/{filmId}")
    public ResponseEntity<Boolean> deleteRating(@RequestHeader(value = "Authorization") String jwtToken,
                                                @PathVariable String filmId) {
                return ResponseEntity.ok(userService.deleteRating(jwtTokenService.getUserIdFromToken(jwtToken), filmId));
    }

    @RequestMapping(value = "/update", method = POST)
    public ResponseEntity<Boolean> updateMovie(@RequestBody Movie movie) {
        Movie optionalMovie = filmService.updateMovie(movie);
        return ResponseEntity.ok(true);
    }

    // TODO how to represent image ?
    @RequestMapping(value = "/update_poster/{id}", method = POST)
    public ResponseEntity<Boolean> updatePoster(@PathVariable String id, @RequestBody String poster) {
        filmService.updatePoster(id, poster);
        return  ResponseEntity.ok(true);
    }

    @RequestMapping(value = "/update_trailer/{id}", method = POST)
    public ResponseEntity<Boolean> updateTrailer(@PathVariable String id, @RequestBody Integer trailer) {
        filmService.updateTrailer(id, trailer);
        return  ResponseEntity.ok(true);
    }

    @RequestMapping(value = "/trailer/{id}", method = GET)
    public ResponseEntity<Set> getTrailers(@PathVariable String id) {
        return new ResponseEntity<>(filmService.getTrailers(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/opening_this_week", method = GET)
    public ResponseEntity<Page> getMoviesOpeningThisWeek(@RequestParam(value = "page", defaultValue = "0") Integer page,@RequestParam(value = "limit", defaultValue = "6") Integer size) {
        Sort sort = new Sort(Sort.Direction.ASC, "releaseDate");
        Pageable pageable = new PageRequest(page, size, sort);
        return new ResponseEntity<>(filmService.getMoviesOpeningThisWeek(pageable), HttpStatus.OK);
    }

    @RequestMapping(value = "/coming_soon", method = GET)
    public ResponseEntity<Page> getMoviesComingSoon(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "limit", defaultValue = "6") Integer size) {
        Sort sort = new Sort(Sort.Direction.ASC, "releaseDate");
        Pageable pageable = new PageRequest(page, size, sort);
        return new ResponseEntity<>(filmService.getMoviesComingSoon(pageable), HttpStatus.OK);
    }

    @RequestMapping(value = "/top_box_office", method = GET)
    public ResponseEntity<Page> getTopBoxOffice(@RequestParam(value = "page", defaultValue = "0") Integer page,@RequestParam(value = "limit", defaultValue = "6") Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "boxOffice");
        Pageable pageable = new PageRequest(page, size, sort);
        return new ResponseEntity<>(filmService.getTopBoxOffice(pageable), HttpStatus.OK);
    }

    @RequestMapping(value = "/top_rating", method = GET)
    public ResponseEntity<Page> getTopRating(@RequestParam(value = "page", defaultValue = "0") Integer page,@RequestParam(value = "limit", defaultValue = "6") Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "rating");
        Pageable pageable = new PageRequest(page, size, sort);
        return new ResponseEntity<>(filmService.getTopRating(pageable), HttpStatus.OK);
    }

    @RequestMapping(value = "/in_theater", method = GET)
    public ResponseEntity<Page> getMoviesInTheater(@RequestParam(value = "page", defaultValue = "0") Integer page,@RequestParam(value = "limit", defaultValue = "6") Integer size) {
        Sort sort = new Sort(Sort.Direction.ASC, "releasedDate");
        Pageable pageable = new PageRequest(page, size, sort);
        return new ResponseEntity<>(filmService.getInTheatersNow(pageable), HttpStatus.OK);
    }


    @RequestMapping(value = "/similar/{id}", method = GET)
    public ResponseEntity<List> getSimilarMovies(@PathVariable String id) {
        return new ResponseEntity<>(filmService.getSimilarMovies(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/in_range", method = GET)
    public ResponseEntity<List> getMoviesInRange(@RequestParam String startDate, @RequestParam String endDate) {
        return new ResponseEntity<>(filmService.getMoviesInRage(startDate, endDate), HttpStatus.OK);
    }

    @RequestMapping(value = "/trending", method = GET)
    public ResponseEntity<List> getMoviesTrending() {
        return new ResponseEntity<>(filmService.getTrending(), HttpStatus.OK);
    }


}
