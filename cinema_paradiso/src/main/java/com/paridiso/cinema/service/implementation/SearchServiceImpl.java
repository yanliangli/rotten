package com.paridiso.cinema.service.implementation;

import com.paridiso.cinema.entity.Celebrity;
import com.paridiso.cinema.entity.Movie;
import com.paridiso.cinema.entity.TV;
import com.paridiso.cinema.persistence.CelebrityRepository;
import com.paridiso.cinema.persistence.MovieRepository;
import com.paridiso.cinema.persistence.TVRepository;
import com.paridiso.cinema.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
//    @Override
//    public List<Movie> getMoviesFromKeyword(String keyword) {
//        List<Movie> movieResult = movieRepository.findMoviesByTitleContains(keyword);
//        return movieResult;
//    }

    @Override
    public Page<Movie> getMoviesFromKeyword(String keyword, Pageable pageable) {
        Page<Movie> movieResult = movieRepository.findMoviesByTitleContains(keyword, pageable);
        return movieResult;
    }

    @Override
    public Page<Celebrity> getCelebritiesFromKeyword(String keyword, Pageable pageable) {
        Page<Celebrity> celebrityResult = celebrityRepository.findCelebritiesByNameContains(keyword, pageable);
        return celebrityResult;
    }

    @Override
    public Page<TV> getTVsFromKeyword(String keyword, Pageable pageable){
        Page<TV> tvResults = tvRepository.findTVByTitleContains(keyword, pageable);
        return tvResults;
    }


    private ArrayList<String> getBasicWords() {
        ArrayList wordsToBeFiltered = new ArrayList();
        wordsToBeFiltered.addAll(Arrays.asList("a", "an", "of", "for", "the"));
        return wordsToBeFiltered;
    }

}
