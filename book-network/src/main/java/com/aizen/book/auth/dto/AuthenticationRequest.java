package com.aizen.book.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @Email(message = "Email is not formatted")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Size(min = 8, message = "Password should be 8 characters long minimum")
    @NotBlank(message = "Password is mandatory")
    private String password;
}
