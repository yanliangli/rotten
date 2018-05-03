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
        Movie movie = movieRepository.findMovieByImdbId(movieId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));
        review.setImdbId(movieId);
        review.setMovieTitle((movie.getTitle()));
        review.setUserId(userId);
        review.setAuthor(user.getUsername());
        List<Review> reviews = user.getUserProfile().getReviews();
        reviews.add(review);
        user.getUserProfile().setReviews(reviews);
        reviews = movie.getReviews();
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
        Movie movie = movieRepository.findMovieByImdbId(filmId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));
        Review reviewToBeRemoved = reviewRepository.findReviewByReviewId(reviewId);
        List<Review> reviews = user.getUserProfile().getReviews();
        for (Review review: reviews) {
            if (review.getReviewId().equals(reviewToBeRemoved.getReviewId())) {
                reviews.remove(reviewToBeRemoved);
                break;
            }
        }
        user.getUserProfile().setReviews(reviews);
        userProfileRepository.save(user.getUserProfile());
        reviews = movie.getReviews();
        for (Review review: reviews) {
            if (review.getReviewId().equals(reviewToBeRemoved.getReviewId())) {
                reviews.remove(reviewToBeRemoved);
                break;
            }
        }
        movie.setReviews(reviews);
        movieRepository.save(movie);
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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));
        List<Review> reviews = user.getUserProfile().getReviews();
        return reviews;

    }
}
