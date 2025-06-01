package com.notesorganizer.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.Date;

@Builder
@Getter
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Instant tokenExpirationTime;

}
