package com.paridiso.cinema.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.paridiso.cinema.entity.User;
import com.paridiso.cinema.entity.WatchList;
import com.paridiso.cinema.security.JwtTokenGenerator;
import com.paridiso.cinema.security.JwtTokenValidator;
import com.paridiso.cinema.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * You can get the userId from jwt token, no need here.
 */

@RequestMapping("/watchlist")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class WatchlistController {

    @Autowired
    @Qualifier(value = "watchlistServiceImpl")
    ListService listService;

    @Autowired
    JwtTokenService jwtTokenService;

    @RequestMapping(value = "/get/watchlist", method = GET)
    public ResponseEntity<List> getWatchlist(@RequestHeader(value = "Authorization") String jwtToken) {
        return new ResponseEntity<>(listService.getList(jwtTokenService.getUserIdFromToken(jwtToken)), HttpStatus.OK);
    }

    @GetMapping(value = "/check/{filmId}")
    public ResponseEntity<Boolean> checkWatchlist(@RequestHeader(value = "Authorization") String jwtToken, @PathVariable String filmId) {
        return ResponseEntity.ok(listService.checkList(jwtTokenService.getUserIdFromToken(jwtToken), filmId));
    }
    @RequestMapping(value = "/addWatchlist", method = POST)
    public ResponseEntity<Boolean> addToWatchlist(@RequestHeader(value = "Authorization") String jwtToken, @RequestParam("filmId") String filmId) {
        return ResponseEntity.ok(listService.addToList(jwtTokenService.getUserIdFromToken(jwtToken), filmId));
    }
    @RequestMapping(value = "/{filmId}", method = DELETE)
    public ResponseEntity<Boolean> removeFromWatchList(@RequestHeader(value = "Authorization") String jwtToken, @PathVariable String filmId) {
        return ResponseEntity.ok(listService.removeFromList(jwtTokenService.getUserIdFromToken(jwtToken), filmId));
    }


}
