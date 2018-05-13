package com.paridiso.cinema.persistence;

import com.paridiso.cinema.entity.TV;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TVRepository extends JpaRepository<TV, String> {
    TV findTVByImdbId(String imdbId);

    Page<TV> findAllByIsNewTonightTrue(Pageable pageable);

    Page<TV> findTVByTitleContains(String keyword, Pageable pageable);

    Page<TV> findAllByRatingAfter(Double rating, Pageable pageable);

    Page<TV> findAllByYearEndsWithAndRatingAfterAndNumberOfRatingsAfter(String endWIth, Double ratingFilter, Integer numberFilter, Pageable pageable);
}
