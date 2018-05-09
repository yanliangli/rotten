package com.paridiso.cinema.persistence;

import com.paridiso.cinema.entity.User;
import com.paridiso.cinema.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
