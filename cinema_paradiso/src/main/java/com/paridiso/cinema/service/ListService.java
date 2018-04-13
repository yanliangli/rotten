package com.paridiso.cinema.service;

import com.paridiso.cinema.entity.Film;
import com.paridiso.cinema.entity.Movie;

import java.util.List;

public interface ListService {

    Integer getSize();

    boolean addToList(Integer userId, String filmId);

    List<Movie> getList(Integer userId);

    boolean removeFromList(Long filmId);

    boolean checkList(Integer userIdFromToken, String filmId);
}
