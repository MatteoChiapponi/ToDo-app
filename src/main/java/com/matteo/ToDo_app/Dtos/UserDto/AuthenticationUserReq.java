package com.matteo.ToDo_app.Dtos.UserDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationUserReq(
        @Email
        String username,
        @NotBlank
        String password
) {
}
