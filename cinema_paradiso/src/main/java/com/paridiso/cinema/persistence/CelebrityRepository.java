package com.paridiso.cinema.persistence;

import com.paridiso.cinema.entity.Celebrity;
import com.paridiso.cinema.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CelebrityRepository extends JpaRepository<Celebrity, String>{
    Celebrity findCelebrityByNameEquals(String name);

    Celebrity findCelebrityByCelebrityId(String celebrityId);

    Page<Celebrity> findCelebritiesByNameContains(String keyword, Pageable pageable);
}
