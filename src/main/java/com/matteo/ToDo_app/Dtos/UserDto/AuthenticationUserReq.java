package com.matteo.ToDo_app.Dtos.UserDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AuthenticationUserReq(
        @Email
        @NotBlank(message = "required username")
        String username,
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[@#$%^&+=]).{6,}$",
                message = "Password must have at least 6 characters, one capital letter and one special character.")
        @NotBlank(message = "required password")
        String password
) {
}
