package com.paridiso.cinema.service;

import com.paridiso.cinema.entity.Film;
import com.paridiso.cinema.entity.Movie;
import com.paridiso.cinema.entity.TV;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public interface UtilityService {

    String getHashedPassword(String passwordToHash, String salt) throws NoSuchAlgorithmException;

    boolean containsMovie(List<Movie> movies, String filmImdbId);

    boolean containsTv(List<TV> tvs, String filmImdbId);

    List<String> tokenizedString(String string);

}
