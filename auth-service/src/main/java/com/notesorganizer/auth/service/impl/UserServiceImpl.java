package com.notesorganizer.auth.service.impl;

import com.notesorganizer.auth.enums.Role;
import com.notesorganizer.auth.model.AccessToken;
import com.notesorganizer.auth.model.User;
import com.notesorganizer.auth.repository.UserRepository;
import com.notesorganizer.auth.service.TokenService;
import com.notesorganizer.auth.service.UserService;
import com.notesorganizer.common.dtos.AuthRequest;
import com.notesorganizer.common.dtos.AuthResponse;
import com.notesorganizer.common.dtos.AuthenticationRequest;
import com.notesorganizer.common.dtos.AuthenticationResponse;
import com.notesorganizer.common.dtos.RegisterRequest;
import com.notesorganizer.common.exceptionhandler.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setFullName(request.getName());
        userRepository.save(user);
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        AccessToken accessToken = tokenService.saveJwtToken(user);
        return AuthResponse.builder()
                .accessToken(accessToken.getAccessToken())
                .refreshToken(accessToken.getRefreshToken().getToken())
                .tokenExpirationTime(accessToken.getAccessTokenExpiry())
                .tokenType("Bearer")
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        String username = jwtService.extractUsername(request.getAccessToken());
        if(Objects.isNull(username)) {
            return buildAuthenticationResponse(false);
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return buildAuthenticationResponse(jwtService.isTokenValid(request.getAccessToken(), user.getUsername()));
    }

    private AuthenticationResponse buildAuthenticationResponse(boolean authenticated) {
        AuthenticationResponse response = new AuthenticationResponse();
        response.setAuthenticated(authenticated);
        return response;
    }
}
