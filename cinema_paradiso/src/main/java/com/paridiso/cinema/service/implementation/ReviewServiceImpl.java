package com.paridiso.cinema.service.implementation;

import com.paridiso.cinema.entity.Movie;
import com.paridiso.cinema.entity.Review;
import com.paridiso.cinema.entity.User;
import com.paridiso.cinema.persistence.MovieRepository;
import com.paridiso.cinema.persistence.ReviewRepository;
import com.paridiso.cinema.persistence.UserProfileRepository;
import com.paridiso.cinema.persistence.UserRepository;
import com.paridiso.cinema.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public List<Review> getAudienceReviews(Long filmId) {
        return null;
    }

    @Override
    public List<Review> getCriticReviews(Long filmId) {
        return null;
    }

    @Transactional
    @Override
    public void addReview(Integer userId, String movieId, Review review) {
        // find movie
        Movie movie = movieRepository.findMovieByImdbId(movieId);

        // find user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));

        //setMovieId to review
        review.setImdbId(movieId);
        //setUserId to review
        review.setUserId(userId);
        // add to user's review list
        List<Review> reviews = user.getUserProfile().getReviews();
        if (reviews == null)
            reviews = new ArrayList<>();
        reviews.add(review);
        user.getUserProfile().setReviews(reviews);
        // add to movie's review list
        reviews = movie.getReviews();
        if (reviews == null)
            reviews = new ArrayList<>();
        reviews.add(review);
        movie.setReviews(reviews);



        Optional.ofNullable(reviewRepository.save(review));
        userProfileRepository.save(user.getUserProfile());
        movieRepository.save(movie);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Review getReview(Long reviewId) {
        return reviewRepository.findReviewByReviewId(reviewId);
    }

    @Override
    public Review removeReview(Integer userId, String filmId, Long reviewId) {
        // find user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));
        // find review and remove
        Review reviewToBeRemoved = reviewRepository.findReviewByReviewId(reviewId);
        List<Review> reviews = user.getUserProfile().getReviews();
        // if id equal, remove review
        for (Review review: reviews) {
            if (review.getReviewId().equals(reviewToBeRemoved.getReviewId())) {
                reviews.remove(reviewToBeRemoved);
                break;
            }
        }
        user.getUserProfile().setReviews(reviews);
        userProfileRepository.save(user.getUserProfile());
        return reviewToBeRemoved;
    }

    @Override
    public boolean likeReview(Long reviewId) {
        return false;
    }

    @Override
    public boolean dislikeReview(Long reviewId) {
        return false;
    }

    @Override
    public boolean detectBadReview(Review review) {
        return false;
    }

    public List<Review> getUserReviews(Integer userId) {
        // find user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));
        List<Review> reviews = user.getUserProfile().getReviews();
        return reviews;

    }
}
