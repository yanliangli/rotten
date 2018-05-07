package com.paridiso.cinema.controller;

import com.paridiso.cinema.entity.Celebrity;
import com.paridiso.cinema.entity.Movie;
import com.paridiso.cinema.entity.TV;
import com.paridiso.cinema.service.SearchService;
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

    @RequestMapping(value = "", method = GET)
    public ResponseEntity<Page> searchMovie(@RequestParam("table") String table, @RequestParam("keyword") String keyword, @RequestParam(value = "page", defaultValue = "0") Integer page,@RequestParam(value = "limit", defaultValue = "7") Integer size) {
        if(table.equals("movie")) {
            Sort sort = new Sort(Sort.Direction.DESC, "rating");
            Pageable pageable = new PageRequest(page, size, sort);
            Page<Movie> moviePage = searchService.getMoviesFromKeyword(keyword, pageable);
            return new ResponseEntity<>(moviePage, HttpStatus.OK);
        }
        else if(table.equals("celebrity")){
            Sort sort = new Sort(Sort.Direction.DESC, "name");
            Pageable pageable = new PageRequest(page, size, sort);
            Page<Celebrity> celebrityPage = searchService.getCelebritiesFromKeyword(keyword, pageable);
            return new ResponseEntity<>(celebrityPage, HttpStatus.OK);
        }
        else{
            Sort sort = new Sort(Sort.Direction.DESC, "rating");
            Pageable pageable = new PageRequest(page, size, sort);
            Page<TV> tvPage = searchService.getTVsFromKeyword(keyword, pageable);
            return new ResponseEntity<>(tvPage, HttpStatus.OK);
        }
    }
}
