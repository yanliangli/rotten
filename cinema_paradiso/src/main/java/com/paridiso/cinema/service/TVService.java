package com.paridiso.cinema.service;

import com.paridiso.cinema.entity.TV;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TVService {
    Page<TV> getNewTVTonight(Pageable pageable);

    Page<TV> getMostPopularTVOnCP(Pageable pageable);
}
