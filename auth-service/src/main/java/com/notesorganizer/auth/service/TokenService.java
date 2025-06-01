package com.notesorganizer.auth.service;

import com.notesorganizer.auth.model.AccessToken;
import com.notesorganizer.auth.model.RefreshToken;
import com.notesorganizer.auth.model.User;

public interface TokenService {

    AccessToken saveJwtToken(User user);

    AccessToken generateJwtToken(User user, RefreshToken refreshToken);

}
