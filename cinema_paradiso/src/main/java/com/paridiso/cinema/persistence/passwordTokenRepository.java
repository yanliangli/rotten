package com.paridiso.cinema.persistence;

import com.paridiso.cinema.entity.PasswordResetToken;
import com.paridiso.cinema.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface passwordTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    PasswordResetToken findByUser(User user);
}
