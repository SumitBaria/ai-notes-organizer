package com.notesorganizer.auth.service.impl;


import com.notesorganizer.auth.model.RefreshToken;
import com.notesorganizer.auth.model.User;
import com.notesorganizer.auth.repository.RefreshTokenRepository;
import com.notesorganizer.auth.service.RefreshTokenService;
import com.notesorganizer.auth.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.notesorganizer.auth.constants.CommonConstants.REFRESH_TOKEN_NOT_FOUND_EXCEPTION;


@Slf4j
@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final JwtService jwtUtils;
    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${jwt.refresh.token.expiration}")
    private long jwtRefreshTokenExpiration;


    @Override
    public RefreshToken saveRefreshToken(User user) {
        RefreshToken refreshToken = build(user);
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken getRefreshToken(String refreshToken) {
        return refreshTokenRepository
                .findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException(REFRESH_TOKEN_NOT_FOUND_EXCEPTION));
    }

    @Override
    public Optional<RefreshToken> getRefreshToken(User user) {
        return refreshTokenRepository.findByUser(user);
    }

    @Override
    public void deleteExpiredRefreshToken(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }

    private RefreshToken build(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtRefreshTokenExpiration));
        refreshToken.setCreatedOn(DateTimeUtils.getCurrentSQLTimestamp());
        return refreshToken;
    }
}
