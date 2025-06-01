package com.notesorganizer.common.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@NotNull
public class AuthenticationRequest {

    private String accessToken;
    private String tokenType;
    private String refreshToken;
}
