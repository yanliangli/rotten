package com.paridiso.cinema.service.implementation;

import com.paridiso.cinema.entity.Celebrity;
import com.paridiso.cinema.entity.Movie;
import com.paridiso.cinema.entity.TV;
import com.paridiso.cinema.persistence.CelebrityRepository;
import com.paridiso.cinema.persistence.MovieRepository;
import com.paridiso.cinema.persistence.TVRepository;
import com.paridiso.cinema.service.CelebrityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier(value = "CelebrityServiceImpl")
public class CelebrityServiceImpl implements CelebrityService {

    @Autowired
    CelebrityRepository celebrityRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    TVRepository tvRepository;

    @Override
    public List<Celebrity> getCelebrities() {
        return celebrityRepository.findAll();
    }

    @Override
    public Celebrity getCelebrity(String celebrityId) {
        return celebrityRepository.findCelebrityByCelebrityId(celebrityId);
    }

    @Override
    public List<Celebrity> getCelebrityListByName(List<String> celebrityName) {
        List<Celebrity> celebrities = new ArrayList<>();
        for(int i = 0; i<celebrityName.size(); i++){
            Celebrity celebrity = celebrityRepository.findCelebrityByNameEquals(celebrityName.get(i));
            if(celebrity != null) {
                celebrities.add(celebrity);
                System.out.println(celebrity.getName());
            }
        }
        return celebrities;
    }

    @Override
    public void deleteCelebrity(String celebrityId) {
        if (celebrityRepository.findCelebrityByCelebrityId(celebrityId) == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Celebrity DOES NOT EXISTS");
        celebrityRepository.deleteById(celebrityId);
    }

    @Override
    public Optional<Celebrity>  updateCelebrity(Celebrity celebrity) {
        if (celebrityRepository.findCelebrityByCelebrityId(celebrity.getCelebrityId()) == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Celebrity DOES NOT EXIST");
        return Optional.ofNullable(celebrityRepository.save(celebrity));
    }

    @Override
    public Optional<Celebrity>  addCelebrity(Celebrity celebrity) {
        if (celebrityRepository.findCelebrityByCelebrityId(celebrity.getCelebrityId()) != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Celebrity ALREADY EXIST");
        return Optional.ofNullable(celebrityRepository.save(celebrity));
    }

    @Override
    public List<Movie> getAllMoviesIn(Celebrity celebrity) {
        List<Movie> movies = new ArrayList<>();
        for(int i=0; i<celebrity.getKnownFor().size(); i++){
            Movie movie = movieRepository.findMovieByImdbId(celebrity.getKnownFor().get(i));
            if(movie !=null){
                movies.add(movie);
            }
        }
        if(movies!=null){
            movies.sort(Comparator.comparing(Movie::getRating).reversed());
        }
        return movies;
    }

    @Override
    public List<TV> getAllTvIn(Celebrity celebrity) {
        List<TV> tvList = new ArrayList<>();
        for(int i=0; i<celebrity.getKnownFor().size(); i++){
            TV tv = tvRepository.findTVByImdbId(celebrity.getKnownFor().get(i));
            if(tv !=null){
                tvList.add(tv);
            }
        }
        if(tvList!=null){
            tvList.sort(Comparator.comparing(TV::getRating).reversed());
        }
        return tvList;
    }
}
