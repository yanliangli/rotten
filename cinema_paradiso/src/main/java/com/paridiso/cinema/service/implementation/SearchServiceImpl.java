package com.paridiso.cinema.service.implementation;

import com.paridiso.cinema.entity.Celebrity;
import com.paridiso.cinema.entity.Movie;
import com.paridiso.cinema.entity.TV;
import com.paridiso.cinema.persistence.CelebrityRepository;
import com.paridiso.cinema.persistence.MovieRepository;
import com.paridiso.cinema.persistence.TVRepository;
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

    @Autowired
    TVRepository tvRepository;

    // @TODO: tokenize the keyword
    @Override
    public List<Movie> getMoviesFromKeyword(String keyword) {
        List<Movie> movieResult = movieRepository.findMoviesByTitleContains(keyword);
        return movieResult;
    }

    @Override
    public List<Celebrity> getCelebritiesFromKeyword(String keyword) {
        List<Celebrity> celebrityResult = celebrityRepository.findCelebritiesByNameContains(keyword);
        return celebrityResult;
    }

    @Override
    public List<TV> getTVsFromKeyword(String keyword){
        List<TV> tvResults = tvRepository.findTVByTitleContains(keyword);
        return tvResults;
    }


    private ArrayList<String> getBasicWords() {
        ArrayList wordsToBeFiltered = new ArrayList();
        wordsToBeFiltered.addAll(Arrays.asList("a", "an", "of", "for", "the"));
        return wordsToBeFiltered;
    }

}
