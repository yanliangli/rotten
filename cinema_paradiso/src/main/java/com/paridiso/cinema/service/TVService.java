package com.paridiso.cinema.service;

import com.paridiso.cinema.entity.TV;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TVService {
    TV updateTV(TV tv);

    Page<TV> getTopRating(Pageable pageable);

    Page<TV> getNewTVTonight(Pageable pageable);

    Page<TV> getMostPopularTVOnCP(Pageable pageable);

    Page<TV> getCertifiedFresh(Pageable pageable);

    
}
