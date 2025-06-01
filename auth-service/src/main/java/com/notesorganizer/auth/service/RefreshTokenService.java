package com.notesorganizer.auth.service;


import com.notesorganizer.auth.model.RefreshToken;
import com.notesorganizer.auth.model.User;

import java.util.Optional;

public interface RefreshTokenService {

    RefreshToken saveRefreshToken(User user);

    RefreshToken getRefreshToken(String refreshToken);

    Optional<RefreshToken> getRefreshToken(User user);

    void deleteExpiredRefreshToken(String refreshToken);
}
