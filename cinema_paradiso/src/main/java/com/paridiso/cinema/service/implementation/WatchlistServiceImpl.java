package com.paridiso.cinema.service.implementation;

import com.paridiso.cinema.entity.*;
import com.paridiso.cinema.persistence.*;
import com.paridiso.cinema.service.ListService;
import com.paridiso.cinema.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@Qualifier(value = "watchlistServiceImpl")
public class WatchlistServiceImpl implements ListService {
    @Autowired
    WatchListRepository watchListRepository;

    @Autowired
    TVRepository tvRepository;

    @Autowired
    MovieRepository movieRepository;

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

    @Override
    public boolean addToList(Integer userId, String filmId) {
        // find movie
        Movie movie = movieRepository.findMovieByImdbId(filmId);
        TV tv = tvRepository.findTVByImdbId(filmId);
        // find user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));

        // check movie existence and size limit
        if(movie != null){
            List<Movie> movies = user.getUserProfile().getWatchList().getMovies();


            if (utilityService.containsMovie(movies, filmId) || movies.size() >= user.getUserProfile().getWatchList().getSizeLimit())
                return false;

            // add to list
            movies.add(movie);
            user.getUserProfile().getWatchList().setMovies(movies);
            System.out.println("watch list id: " + user.getUserProfile().getWatchList().getWatchListId());
            watchListRepository.save(user.getUserProfile().getWatchList());

        }else {
            List<TV> tvs = user.getUserProfile().getWatchList().getTvs();
            if (utilityService.containsTv(tvs, filmId) || tvs.size() >= user.getUserProfile().getWatchList().getSizeLimit())
                return false;
            tvs.add(tv);
            user.getUserProfile().getWatchList().setTvs(tvs);
            watchListRepository.save(user.getUserProfile().getWatchList());
        }
        return true;

    }

    @Override
    public List<Movie> getList(Integer userId) {
        // find user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));

        List<Movie> movies = user.getUserProfile().getWatchList().getMovies();
        return movies;
    }

    @Override
    public List<TV> getTVList(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));

        List<TV> tvs = user.getUserProfile().getWatchList().getTvs();
        return tvs;
    }

    @Override
    public boolean removeFromList(Integer userId, String filmId) {
        // find movie
        Movie movie = movieRepository.findMovieByImdbId(filmId);
        TV tv = tvRepository.findTVByImdbId(filmId);

        // find user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));

        // check movie existence
        if(movie != null){
            List<Movie> movies = user.getUserProfile().getWatchList().getMovies();


            if (utilityService.containsMovie(movies, filmId)){
                movies.remove(movie);
                watchListRepository.save(user.getUserProfile().getWatchList());
                return true;
            }
        } else {
            List<TV> tvs = user.getUserProfile().getWatchList().getTvs();


            if (utilityService.containsTv(tvs, filmId)){
                tvs.remove(tv);
                watchListRepository.save(user.getUserProfile().getWatchList());
                return true;
            }
        }


        return false;
    }

    @Override
    public boolean checkList(Integer userId, String filmImdbId){

        // find user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "USER NOT FOUND"));

        // check movie existence
        List<Movie> movies = user.getUserProfile().getWatchList().getMovies();
        List<TV> tvs = user.getUserProfile().getWatchList().getTvs();

        if (utilityService.containsMovie(movies, filmImdbId)||utilityService.containsTv(tvs,filmImdbId))
            return true;
        return false;
    }

}
