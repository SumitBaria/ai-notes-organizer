package com.notesorganizer.auth.repository;

import com.notesorganizer.auth.model.AccessToken;
import com.notesorganizer.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {

    Optional<AccessToken> findByUser(User user);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void deleteByUser(User user);
}
