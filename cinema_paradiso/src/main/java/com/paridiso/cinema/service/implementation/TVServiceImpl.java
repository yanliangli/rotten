package com.paridiso.cinema.service.implementation;

import com.paridiso.cinema.entity.Film;
import com.paridiso.cinema.entity.Movie;
import com.paridiso.cinema.entity.TV;
import com.paridiso.cinema.entity.Trailer;
import com.paridiso.cinema.persistence.TVRepository;
import com.paridiso.cinema.service.TVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.paridiso.cinema.service.UtilityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Qualifier(value = "TVServiceImpl")
public class TVServiceImpl implements TVService {
    @Autowired
    TVRepository tvRepository;

    @Transactional
    @Override
    public TV getTV(String filmId) {
        return tvRepository.findTVByImdbId(filmId);
    }

    @Transactional
    @Override
    public TV updateTV(TV tv) {
        if (tvRepository.findTVByImdbId(tv.getImdbId()) == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MOVIE DOES NOT EXIST");
        return tvRepository.save(tv);
    }

    @Override
    public Page<TV> getNewTVTonight(Pageable pageable){
        return tvRepository.findAllByIsNewTonightTrue(pageable);
    }

    @Override
    public Page<TV> getMostPopularTVOnCP(Pageable pageable){
        Double ratingFilter = 3.49;
        return tvRepository.findAllByRatingAfter(ratingFilter, pageable);
    }

    @Override
    public Page<TV> getTopRating(Pageable pageable){
        return tvRepository.findAll(pageable);
    }

    @Override
    public Page<TV> getCertifiedFresh(Pageable pageable){
        Double ratingFilter = 3.49;
        Integer numberFilter = 25;
        String ending = "2018";
        Page<TV> freshTv = tvRepository.findAllByYearEndsWithAndRatingAfterAndNumberOfRatingsAfter(ending, ratingFilter, numberFilter, pageable);
        return freshTv;
    }

}
