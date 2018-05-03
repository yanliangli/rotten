package com.paridiso.cinema.persistence;

import com.paridiso.cinema.entity.TV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TVRepository extends JpaRepository<TV, String> {
    List<TV> findAllByReleaseDate(Date date);

    List<TV> findTop60ByOrderByAudienceRating();

    List<TV> findTVByTitleContains(String keyword);
}
