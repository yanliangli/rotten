package com.paridiso.cinema.controller;

import com.paridiso.cinema.entity.TV;
import com.paridiso.cinema.service.JwtTokenService;
import com.paridiso.cinema.service.TVService;
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

import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RequestMapping("/tv")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TVController {

    @Autowired
    @Qualifier("TVServiceImpl")
    TVService tvService;

    @Autowired
    JwtTokenService jwtTokenService;

    @Autowired
    RegUserServiceImpl userService;

    @RequestMapping(value = "/all", method = GET)
    public ResponseEntity<List> getAllTV() {
        return null;
    }


    @RequestMapping(value = "/{id}", method = GET)
    public ResponseEntity<TV> getTV(@PathVariable Long id) {
        return null;
    }

    @RequestMapping(value = "/add_tv", method = POST)
    public ResponseEntity<Boolean> addTV(@RequestBody TV tv) {
        return null;
    }


    @RequestMapping(value = "/{id}", method = DELETE)
    public ResponseEntity<Boolean> deleteTV(@PathVariable Long id) {
        return null;
    }


    @RequestMapping(value = "/rate_tv/{id}", method = POST)
    public ResponseEntity<Boolean> rateTV(@PathVariable Long id,
                                          @RequestParam(value = "rating", required = true) Double rating) {
        return null;
    }

    @RequestMapping(value = "/update_tv", method = POST)
    public ResponseEntity<Boolean> updateTV(@RequestBody TV tv) {
        return null;
    }

    // TODO how to represent image ?
    @RequestMapping(value = "/{id}/update_poster", method = POST)
    public ResponseEntity<Boolean> updatePoster(@PathVariable Integer id, @RequestBody String poster) {
        return null;
    }

    @RequestMapping(value = "/{id}/update_trailer", method = POST)
    public ResponseEntity<Boolean> updateTrailer(@PathVariable Integer id, @RequestBody String trailer) {
        return null;
    }

    @RequestMapping(value = "/{id}/trailer", method = GET)
    public ResponseEntity<Boolean> getTrailers(@PathVariable Integer id) {
        return null;
    }

    @RequestMapping(value = "/trending", method = GET)
    public ResponseEntity<List> getTVsTrending() {
        return null;
    }

    @RequestMapping(value = "/playing", method = GET)
    public ResponseEntity<List> getTVsPlaying() {
        return null;
    }

    @RequestMapping(value = "/top_rating", method = GET)
    public ResponseEntity<List> getTVsTopRatings() {
        return null;
    }

    @RequestMapping(value = "/{id}/similar", method = GET)
    public ResponseEntity<List> getSimilarTVs(@PathVariable Integer id) {
        return null;
    }

    @RequestMapping(value = "/range", method = GET)
    public ResponseEntity<List> getTVsInRange(@RequestParam Date startDate, @RequestParam Date endDate) {
        return null;
    }

    @RequestMapping(value = "/new_tv_tonight", method = GET)
    public ResponseEntity<Page> getNewTVTonight(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "limit", defaultValue = "6") Integer size) {
        Sort sort = new Sort(Sort.Direction.ASC, "rating");
        Pageable pageable = new PageRequest(page, size, sort);
        return new ResponseEntity<>(tvService.getNewTVTonight(pageable), HttpStatus.OK);
    }

    @RequestMapping(value = "/most_popular", method = GET)
    public ResponseEntity<Page> getMostPopularTVOnCP(@RequestParam(value = "page", defaultValue = "0") Integer page,@RequestParam(value = "limit", defaultValue = "6") Integer size) {
        Sort sort = new Sort(Sort.Direction.ASC, "rating");
        Pageable pageable = new PageRequest(page, size, sort);
        return new ResponseEntity<>(tvService.getMostPopularTVOnCP(pageable), HttpStatus.OK);
    }




}
