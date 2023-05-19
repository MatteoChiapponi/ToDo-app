package com.matteo.ToDo_app.Dtos.UserDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserDto(
        @NotBlank(message = "cannot be blank")
        String firstName,
        @NotBlank(message = "cannot be blank")
        String lastName,
        @Email(message = "invalid format email, must be like test@test.com")
        String email,
        @NotBlank(message = "cannot be blank")
        String password,
        @NotBlank(message = "cannot be blank")
        String repitedPassword
) {

}
