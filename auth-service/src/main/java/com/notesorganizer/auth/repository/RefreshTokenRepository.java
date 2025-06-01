package com.notesorganizer.auth.repository;


import com.notesorganizer.auth.model.RefreshToken;
import com.notesorganizer.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void deleteByToken(String token);
}