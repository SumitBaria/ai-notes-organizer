package com.notesorganizer.common.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NotNull
public class AuthRequest {

    @Email(message = "Invalid username. It should be email address")
    private String username;
    private String password;
}
