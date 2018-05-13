package com.paridiso.cinema.persistence;

import com.paridiso.cinema.entity.FollowList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowListRepository extends JpaRepository<FollowList, Integer> {
}
