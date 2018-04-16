package com.paridiso.cinema.persistence;

import com.paridiso.cinema.entity.RatedMovieList;
import com.paridiso.cinema.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatedMovieListRepository extends JpaRepository<RatedMovieList, Integer> {
}
