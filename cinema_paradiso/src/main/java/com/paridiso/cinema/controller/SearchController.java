package com.paridiso.cinema.controller;

import com.paridiso.cinema.entity.Celebrity;
import com.paridiso.cinema.entity.Movie;
import com.paridiso.cinema.entity.TV;
import com.paridiso.cinema.service.SearchService;
import com.paridiso.cinema.service.implementation.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RequestMapping("/search")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SearchController {

    @Autowired
    SearchService searchService;

    @Autowired
    MovieServiceImpl filmService;

    @RequestMapping(value = "/movie", method = GET)
    public ResponseEntity<Page> searchMovie(@RequestParam("keyword") String keyword, @RequestParam(value = "page", defaultValue = "0") Integer page,@RequestParam(value = "itemsPerPage", defaultValue = "7") Integer size, @RequestParam(value = "sortBy", defaultValue = "rating") String sortBy, @RequestParam(value = "order", defaultValue = "DESC") String order) {
        Sort sort = filmService.getSortParam(order, sortBy);
        Pageable pageable = new PageRequest(page, size, sort);
        Page<Movie> moviePage = searchService.getMoviesFromKeyword(keyword, pageable);
        return new ResponseEntity<>(moviePage, HttpStatus.OK);
    }

    @RequestMapping(value = "/celebrity", method = GET)
    public ResponseEntity<Page> searchCelebrity(@RequestParam("keyword") String keyword, @RequestParam(value = "page", defaultValue = "0") Integer page,@RequestParam(value = "itemsPerPage", defaultValue = "7") Integer size, @RequestParam(value = "sortBy", defaultValue = "name") String sortBy, @RequestParam(value = "order", defaultValue = "ASC") String order) {
        Sort sort = filmService.getSortParam(order, sortBy);
        Pageable pageable = new PageRequest(page, size, sort);
        Page<Celebrity> celebrityPage = searchService.getCelebritiesFromKeyword(keyword, pageable);
        return new ResponseEntity<>(celebrityPage, HttpStatus.OK);
    }

    @RequestMapping(value = "/tv", method = GET)
    public ResponseEntity<Page> searchTV(@RequestParam("keyword") String keyword, @RequestParam(value = "page", defaultValue = "0") Integer page,@RequestParam(value = "itemsPerPage", defaultValue = "7") Integer size, @RequestParam(value = "sortBy", defaultValue = "rating") String sortBy, @RequestParam(value = "order", defaultValue = "DESC") String order) {
        Sort sort = filmService.getSortParam(order, sortBy);
        Pageable pageable = new PageRequest(page, size, sort);
        Page<TV> tvPage = searchService.getTVsFromKeyword(keyword, pageable);
        return new ResponseEntity<>(tvPage, HttpStatus.OK);
    }
}
