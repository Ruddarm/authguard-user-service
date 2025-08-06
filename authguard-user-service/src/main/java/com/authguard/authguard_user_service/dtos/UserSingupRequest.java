package com.authguard.authguard_user_service.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSingupRequest {
    @NotBlank(message = "Name is empty")
    @NotNull(message = "Name is required")
    @Size(min = 2)
    private String firstName;
    @NotBlank(message = "Name is empty")
    @NotNull(message = "Name is required")
    @Size(min = 2)
    private String lastName;
    @NotBlank(message = "Password is empty")
    @NotNull(message = "password is requird")
    @Size(min = 8)
    private String password;
    private String country;
    @Email(message = "Email is not valid")
    @NotBlank(message = "Email is empty")
    @NotNull(message = "Email is required")
    private String email;
}
