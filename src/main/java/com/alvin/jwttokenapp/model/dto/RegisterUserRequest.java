package com.alvin.jwttokenapp.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegisterUserRequest {
    private long id;
    @NotBlank
    @Size(min = 5, max = 32, message = "Username must be between length of 8 to 32.")
    private String username;
    @Size(min = 8, max = 50, message = "Password must be between length of 8 to 50.")
    private String password;
}