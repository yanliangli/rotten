package com.paridiso.cinema.controller;

import com.paridiso.cinema.entity.Movie;
import com.paridiso.cinema.entity.Review;
import com.paridiso.cinema.service.JwtTokenService;
import com.paridiso.cinema.service.ReviewService;
import com.paridiso.cinema.service.implementation.ReviewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewController {

    @Autowired
    ReviewServiceImpl reviewService;

    @Autowired
    JwtTokenService jwtTokenService;

    @RequestMapping(value = "/{filmId}/review", method = POST)
    public ResponseEntity addReview(@RequestHeader(value = "Authorization") String jwtToken,
                                             @PathVariable String filmId,
                                             @RequestBody Review review) {
        System.out.println("qwdqwdqwd");
        reviewService.addReview(jwtTokenService.getUserIdFromToken(jwtToken), filmId, review);
        return new ResponseEntity(HttpStatus.OK);
    }

//    @RequestMapping(value = "/{filmId}", method = GET, params = "reviewId")
//    public ResponseEntity<Review> getReview(@PathVariable String filmId,
//                                          @RequestParam Long reviewId) {
//
//        Review review = reviewService.getReview(reviewId).orElseThrow(() ->
//                new ResponseStatusException(BAD_REQUEST, "Unable to get review"));
//
//        return new ResponseEntity<>(review, HttpStatus.OK);
//    }
//
//

    @RequestMapping(value = "/{filmId}/review", method = DELETE, params = "reviewId")
    public ResponseEntity<Review> deleteReview(@RequestHeader(value = "Authorization") String jwtToken,
                                                @PathVariable String filmId,
                                                @RequestParam Long reviewId) {
        Review reviewToBeRemovoed = reviewService.removeReview(jwtTokenService.getUserIdFromToken(jwtToken),filmId, reviewId);
        if (reviewToBeRemovoed != null)
            return new ResponseEntity<>(reviewToBeRemovoed, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/all_reviews", method = GET)
    public ResponseEntity<List> getAllReviews() {
        return new ResponseEntity<>(reviewService.getAllReviews(), HttpStatus.OK);
    }


    @RequestMapping(value = "/{reviewId}", method = GET)
    public ResponseEntity<Review> getReview(@PathVariable Long reviewId) {
        Review review = reviewService.getReview(reviewId);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    //@TODO reviews
    @RequestMapping(value = "movie/{movieId}/reviews", method = GET)
    public ResponseEntity<List> getMovieReviews(@PathVariable String movieId) {
        return null;
    }

    @RequestMapping(value = "user/{userId}/reviews", method = GET)
    public ResponseEntity<List> getUserReviews(@PathVariable Integer userId) {
        return null;
    }


    @RequestMapping(value = "user/update_review/{reviewIDd", method = POST)
    public ResponseEntity<Boolean> updateReview(@PathVariable Integer reviewId,
                                                @RequestParam(value = "review_content", required = true) String reviewContent) {
        return null;
    }

    @RequestMapping(value = "/{movieId}/review", method = POST, params = "liked")
    public ResponseEntity<Boolean> likeReview(@PathVariable String movieId,
                                              @RequestParam Long reviewId,
                                              @RequestParam Boolean liked) {
        return null;
    }

    @RequestMapping(value = "movie/{movieId}/dislike_review/{reviewId}", method = POST)
    public ResponseEntity<Boolean> dislikeReview(@PathVariable Long movieId,
                                                 @PathVariable Long reviewId) {
        return null;
    }


    @RequestMapping(value = "/", method = GET)
    public String welcome() {
        return "Welcome to cinema paradiso";
    }



}
