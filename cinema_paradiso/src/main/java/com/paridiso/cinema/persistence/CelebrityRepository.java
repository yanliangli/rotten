package com.paridiso.cinema.persistence;

import com.paridiso.cinema.entity.Celebrity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CelebrityRepository extends JpaRepository<Celebrity, String>{
    Celebrity findCelebrityByCelebrityId(String celebrityId);

    List<Celebrity> findCelebritiesByName(String keyword);

    List<Celebrity> findCelebritiesByNameContains(String keyword);
}
