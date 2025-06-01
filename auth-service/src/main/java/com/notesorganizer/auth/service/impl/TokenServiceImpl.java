package com.notesorganizer.auth.service.impl;

import com.notesorganizer.auth.model.AccessToken;
import com.notesorganizer.auth.model.RefreshToken;
import com.notesorganizer.auth.model.User;
import com.notesorganizer.auth.repository.AccessTokenRepository;
import com.notesorganizer.auth.service.RefreshTokenService;
import com.notesorganizer.auth.service.TokenService;
import com.notesorganizer.auth.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl  implements TokenService {

    private final RefreshTokenService refreshTokenService;
    private final AccessTokenRepository accessTokenRepository;
    private final JwtService jwtService;

    @Override
    public AccessToken saveJwtToken(User user) {
        return refreshTokenService
                .getRefreshToken(user)
                .map(refreshToken -> validateAndCreateToken(user, refreshToken))
                .orElseGet(() -> validateAndCreateToken(user, refreshTokenService.saveRefreshToken(user)));
    }

    private AccessToken validateAndCreateToken(User user, RefreshToken refreshToken) {
        Optional<AccessToken> accessToken = accessTokenRepository.findByUser(user);
        if(accessToken.isPresent() && accessToken.get().getAccessTokenExpiry().isAfter(DateTimeUtils.getCurrentSQLTimestamp().toInstant())) {
            return accessToken.get();
        } else {
            accessTokenRepository.deleteByUser(user);
            return saveToken(user, refreshToken);
        }
    }


    private AccessToken saveToken(User user, RefreshToken refreshToken) {
        String token = jwtService.generateTokenFromUserName(user.getUsername());
        return accessTokenRepository
                .save(buildJwtToken(user, token, refreshToken));
    }

    private AccessToken buildJwtToken(User user, String token, RefreshToken refreshToken) {
        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken(token);
        accessToken.setUser(user);
        accessToken.setAccessTokenExpiry(jwtService.extractExpiration(token).toInstant());
        accessToken.setRefreshToken(refreshToken);
        return accessToken;
    }

    @Override
    @Transactional
    public AccessToken generateJwtToken(User user, RefreshToken refreshToken) {
        accessTokenRepository.deleteByUser(user);
        String token = jwtService.generateTokenFromUserName(user.getUsername());
        AccessToken accessToken = buildJwtToken(user, token, refreshToken);
        return accessTokenRepository.save(accessToken);
    }
}
