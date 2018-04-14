package com.paridiso.cinema.controller;

import com.paridiso.cinema.entity.Celebrity;
import com.paridiso.cinema.service.implementation.CelebrityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RequestMapping("/celebrity")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CelebrityController {


    @Autowired
    @Qualifier("CelebrityServiceImpl")
    CelebrityServiceImpl celebrityService;

    @RequestMapping(value = "/all_celebrities", method = GET)
    public ResponseEntity<List> getCelebrities() {
        return ResponseEntity.ok(celebrityService.getCelebrities());
    }

    @RequestMapping(value = "/add", method = POST)
    public ResponseEntity<Boolean> addCelebrity(@RequestBody final Celebrity celebrity) {
        Celebrity optionalCelebrity = celebrityService.addCelebrity(celebrity).orElseThrow(() ->
                new ResponseStatusException(BAD_REQUEST, "Unable to add celebrity"));
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "/{id}", method = GET)
    public ResponseEntity<Celebrity> getCelebrity(@PathVariable String id) {
        Celebrity celebrity = celebrityService.getCelebrity(id);
        if (celebrity != null)
            return new ResponseEntity<>(celebrity, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public ResponseEntity<Boolean> deleteCelebrity(@PathVariable String id) {
        celebrityService.deleteCelebrity(id);
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "/update", method = POST)
    public ResponseEntity<Boolean> updateCelebrity(@RequestBody final Celebrity celebrity) {
        Celebrity optionalCelebrity = celebrityService.updateCelebrity(celebrity).orElseThrow(() ->
                new ResponseStatusException(BAD_REQUEST, "Unable to update celebrity"));
        return ResponseEntity.ok(true);
    }
//    @RequestMapping(value = "/{id}", method = POST)
//    public ResponseEntity<Celebrity> deleteCelebrity(@PathVariable Integer id,
//                                                     @RequestBody final Celebrity celebrity) {
//        return null;
//    }

}
