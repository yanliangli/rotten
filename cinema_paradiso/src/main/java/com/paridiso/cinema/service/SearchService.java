package com.paridiso.cinema.service;

import com.paridiso.cinema.entity.Celebrity;
import com.paridiso.cinema.entity.Movie;
import com.paridiso.cinema.entity.TV;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public interface SearchService {
    //List<Movie> getMoviesFromKeyword(String keyword);
    Page<Movie> getMoviesFromKeyword(String keyword, Pageable pageable);
    //List<Movie> getMoviesFromGenre(String genre);
    //List<Movie> getMoviesFromCelebrityName(String celebrityName);

    Page<Celebrity> getCelebritiesFromKeyword(String keyword, Pageable pageable);
    Page<TV> getTVsFromKeyword(String keyword, Pageable pageable);

}
