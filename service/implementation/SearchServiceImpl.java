package com.paridiso.cinema.service.implementation;

import com.paridiso.cinema.entity.Celebrity;
import com.paridiso.cinema.entity.Movie;
import com.paridiso.cinema.persistence.CelebrityRepository;
import com.paridiso.cinema.persistence.MovieRepository;
import com.paridiso.cinema.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchServiceImpl implements SearchService{

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    CelebrityRepository celebrityRepository;

    // @TODO: tokenize the keyword
    // @TODO: celebrity search
    @Override
    public List<Movie> getMoviesFromKeyword(String keyword) {
        // LinkedHashSet: no duplicates and allow ordering
        LinkedHashSet<Movie> movieSet = new LinkedHashSet<>();
        movieSet.addAll(getMoviesExactMatch(keyword));
        movieSet.addAll(getMoviesPhraseMatch(keyword));
        List<Movie> movieList = new ArrayList<>(new LinkedHashSet<>());
        movieList.addAll(movieSet);
        return movieList;
    }

    private List<Movie> getMoviesExactMatch(String keyword) {
        return movieRepository.findMoviesByTitle(keyword);
    }

    private List<Movie> getMoviesPhraseMatch(String keyword) {
        return movieRepository.findMoviesByTitleContains(keyword);
    }

    @Override
    public List<Celebrity> getCelebritiesFromKeyword(String keyword) {
        // LinkedHashSet: no duplicates and allow ordering
        LinkedHashSet<Celebrity> celebritySet = new LinkedHashSet<>();
        celebritySet.addAll(getCelebritiesExactMatch(keyword));
        celebritySet.addAll(getCelebritiesPhraseMatch(keyword));
        List<Celebrity> celebrityList = new ArrayList<>(new LinkedHashSet<>());
        celebrityList.addAll(celebritySet);
        return celebrityList;
    }

    private List<Celebrity> getCelebritiesExactMatch(String keyword) {
        return celebrityRepository.findCelebritiesByName(keyword);
    }

    private List<Celebrity> getCelebritiesPhraseMatch(String keyword) {
        return celebrityRepository.findCelebritiesByNameContains(keyword);
    }



    private ArrayList<String> getBasicWords() {
        ArrayList wordsToBeFiltered = new ArrayList();
        wordsToBeFiltered.addAll(Arrays.asList("a", "an", "of", "for", "the"));

        return wordsToBeFiltered;
    }

}
