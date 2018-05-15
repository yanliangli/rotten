package com.paridiso.cinema.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.paridiso.cinema.entity.User;
import com.paridiso.cinema.entity.WatchList;
import com.paridiso.cinema.security.JwtTokenGenerator;
import com.paridiso.cinema.security.JwtTokenValidator;
import com.paridiso.cinema.service.JwtTokenService;
import com.paridiso.cinema.service.ListService;
import com.paridiso.cinema.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.paridiso.cinema.service.WishlistService;
import org.springframework.core.env.Environment;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * You can get the userId from jwt token, no need here.
 */


@RequestMapping("/wishlist")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class WishlistController {

    @Autowired
    @Qualifier(value = "wishlistServiceImpl")
    ListService listService;

    @Autowired
    JwtTokenService jwtTokenService;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/get/wishlistMovies", method = GET)
    public ResponseEntity<List> getWishlistMovies(@RequestHeader(value = "Authorization") String jwtToken) {
        return new ResponseEntity<>(listService.getList(jwtTokenService.getUserIdFromToken(jwtToken)), HttpStatus.OK);
    }
    @RequestMapping(value = "/get/wishlistMovies/{userId}", method = GET)
    public ResponseEntity<List> getWishlistMovies(@PathVariable Integer userId) {
        return new ResponseEntity<>(listService.getList(userId), HttpStatus.OK);
    }

    @RequestMapping(value = "/get/wishlistTvs", method = GET)
    public ResponseEntity<List> getWishlistTvs(@RequestHeader(value = "Authorization") String jwtToken) {
        return new ResponseEntity<>(listService.getTVList(jwtTokenService.getUserIdFromToken(jwtToken)), HttpStatus.OK);
    }
    @RequestMapping(value = "/get/wishlistTvs/{userId}", method = GET)
    public ResponseEntity<List> getWishlistTvs(@PathVariable Integer userId) {
        return new ResponseEntity<>(listService.getTVList(userId), HttpStatus.OK);
    }
    @GetMapping(value = "/check/{filmId}")
    public ResponseEntity<Boolean> checkWishlist(@RequestHeader(value = "Authorization") String jwtToken,
                                                 @PathVariable String filmId) {
        return ResponseEntity.ok(listService.checkList(jwtTokenService.getUserIdFromToken(jwtToken), filmId));
    }
    // http://localhost:8080/wishlist?filmId=1
    @RequestMapping(value = "/addWishlist", method = POST)
    public ResponseEntity<Boolean> addToWishList(@RequestHeader(value = "Authorization") String jwtToken,
                                                 @RequestParam("filmId") String filmId) {
        return ResponseEntity.ok(listService.addToList(jwtTokenService.getUserIdFromToken(jwtToken), filmId));
    }

    @RequestMapping(value = "/{filmId}", method = DELETE)
    public ResponseEntity<Boolean> removeFromWishList(@RequestHeader(value = "Authorization") String jwtToken,
                                                      @PathVariable String filmId) {
        return ResponseEntity.ok(listService.removeFromList(jwtTokenService.getUserIdFromToken(jwtToken), filmId));
    }
}
