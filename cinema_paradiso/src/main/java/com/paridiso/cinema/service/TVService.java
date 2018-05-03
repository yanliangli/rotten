package com.paridiso.cinema.service;

import com.paridiso.cinema.entity.TV;

import java.util.List;

public interface TVService {
    List<TV> getNewTVTonight();

    List<TV> getMostPopularTVOnCP();
}
