package com.matteo.ToDo_app.Dtos.UserDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterUserReq(
        @NotBlank(message = "required firstName")
        String firstName,
        @NotBlank(message = "required lastName")
        String lastName,
        @Email(message = "invalid format email, must be like test@test.com")
        @NotBlank(message = "required email")
        String email,
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[@#$%^&+=]).{6,}$",
                message = "Password must have at least 6 characters, one capital letter and one special character.")
        @NotBlank(message = "required password")
        String password,
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[@#$%^&+=]).{6,}$",
                message = "Password must have at least 6 characters, one capital letter and one special character.")
        @NotBlank(message = "required repitedPassword")
        String repitedPassword
) {

}
