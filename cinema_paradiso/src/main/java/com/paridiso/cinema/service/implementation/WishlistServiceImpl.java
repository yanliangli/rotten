package com.paridiso.cinema.service.implementation;

import com.paridiso.cinema.entity.*;
import com.paridiso.cinema.persistence.*;
import com.paridiso.cinema.service.ListService;
import com.paridiso.cinema.service.UtilityService;
import com.paridiso.cinema.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@Qualifier(value = "wishlistServiceImpl")
public class WishlistServiceImpl implements ListService, WishlistService {

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    TVRepository tvRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    UtilityService utilityService;

    @Override
    public Integer getSize() {
        return null;
    }

    @Transactional
    @Override
    public boolean addToList(Integer userId, String filmId) {
        Movie movie = movieRepository.findMovieByImdbId(filmId);
        TV tv = tvRepository.findTVByImdbId(filmId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));
        if(movie != null){
            List<Movie> movies = user.getUserProfile().getWishList().getMovies();
            if (utilityService.containsMovie(movies, filmId) || movies.size() >= user.getUserProfile().getWishList().getSizeLimit())
                return false;
            movies.add(movie);
            user.getUserProfile().getWishList().setMovies(movies);
            wishListRepository.save(user.getUserProfile().getWishList());
        } else {
            List<TV> tvs = user.getUserProfile().getWishList().getTvs();
            if (utilityService.containsTv(tvs, filmId) || tvs.size() >= user.getUserProfile().getWishList().getSizeLimit())
                return false;
            tvs.add(tv);
            user.getUserProfile().getWishList().setTvs(tvs);
            wishListRepository.save(user.getUserProfile().getWishList());
        }
        return true;
    }

    @Override
    public List<Movie> getList(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));
        List<Movie> movies = user.getUserProfile().getWishList().getMovies();
        return movies;
    }

    @Override
    public List<TV> getTVList(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));
        List<TV> tvs = user.getUserProfile().getWishList().getTvs();
        return tvs;
    }

    @Override
    public boolean removeFromList(Integer userId, String filmId) {
        Movie movie = movieRepository.findMovieByImdbId(filmId);
        TV tv = tvRepository.findTVByImdbId(filmId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));
        if(movie != null){
            List<Movie> movies = user.getUserProfile().getWishList().getMovies();
            if (utilityService.containsMovie(movies, filmId)){
                movies.remove(movie);
                wishListRepository.save(user.getUserProfile().getWishList());
                return true;
            }
        } else {
            List<TV> tvs = user.getUserProfile().getWishList().getTvs();
            if (utilityService.containsTv(tvs, filmId)){
                tvs.remove(tv);
                wishListRepository.save(user.getUserProfile().getWishList());
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean checkList(Integer userId, String filmImdbId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));
        List<Movie> movies = user.getUserProfile().getWishList().getMovies();
        List<TV> tvs = user.getUserProfile().getWishList().getTvs();
        if (utilityService.containsMovie(movies, filmImdbId)||utilityService.containsTv(tvs,filmImdbId))
            return true;
        return false;
    }

}
