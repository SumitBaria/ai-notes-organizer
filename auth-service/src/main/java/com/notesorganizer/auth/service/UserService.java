package com.notesorganizer.auth.service;


import com.notesorganizer.common.dtos.AuthRequest;
import com.notesorganizer.common.dtos.AuthResponse;
import com.notesorganizer.common.dtos.AuthenticationRequest;
import com.notesorganizer.common.dtos.AuthenticationResponse;
import com.notesorganizer.common.dtos.RegisterRequest;

public interface UserService {

    void register(RegisterRequest request);
    AuthResponse login(AuthRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
