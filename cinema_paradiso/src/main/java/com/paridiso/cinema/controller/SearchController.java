package com.paridiso.cinema.controller;

import com.paridiso.cinema.entity.Celebrity;
import com.paridiso.cinema.entity.Movie;
import com.paridiso.cinema.entity.TV;
import com.paridiso.cinema.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "", method = GET)
    public ResponseEntity<List> searchMovie(@RequestParam("table") String table, @RequestParam("keyword") String keyword) {
        if(table.equals("movie")) {
            List<Movie> movieList = searchService.getMoviesFromKeyword(keyword);
            return new ResponseEntity<>(movieList, HttpStatus.OK);
        }
        else if(table.equals("celebrity")){
            List<Celebrity> celebrityList = searchService.getCelebritiesFromKeyword(keyword);
            return new ResponseEntity<>(celebrityList, HttpStatus.OK);
        }
        else{
            List<TV> tvList = searchService.getTVsFromKeyword(keyword);
            return new ResponseEntity<>(tvList, HttpStatus.OK);
        }
    }
}
