package com.paridiso.cinema.persistence;

import com.paridiso.cinema.entity.CriticApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriticApplicationRepository extends JpaRepository<CriticApplication, Integer> {
    CriticApplication findCriticApplicationByUserId(Integer id);
}
