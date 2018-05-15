package com.paridiso.cinema.service;

import com.paridiso.cinema.entity.Movie;
import com.paridiso.cinema.entity.TV;

import java.util.List;

public interface ListService {

    Integer getSize();

    boolean addToList(Integer userId, String filmId);

    List<Movie> getList(Integer userId);

    List<TV> getTVList(Integer userId);

    boolean removeFromList(Integer userIdFromToken, String filmId);

    boolean checkList(Integer userIdFromToken, String filmId);
}
