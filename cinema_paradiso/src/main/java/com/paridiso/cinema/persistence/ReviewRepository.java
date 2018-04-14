package com.paridiso.cinema.persistence;

import com.paridiso.cinema.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
    Review findReviewByReviewId(Long reviewId);
}
