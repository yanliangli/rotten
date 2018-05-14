package com.paridiso.cinema.service;

import com.paridiso.cinema.entity.Celebrity;

import java.util.List;
import java.util.Optional;

public interface CelebrityService {

    List<Celebrity> getCelebrities();

    Celebrity getCelebrity(String celebrityId);

    List<Celebrity> getCelebrityListByName(List<String> celebrityName);

    void deleteCelebrity(String celebrityId);

    Optional<Celebrity> updateCelebrity(Celebrity celebrity);

    Optional<Celebrity> addCelebrity(Celebrity celebrity);
}
