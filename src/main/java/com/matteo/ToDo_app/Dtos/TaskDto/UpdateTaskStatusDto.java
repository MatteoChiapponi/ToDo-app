package com.matteo.ToDo_app.Dtos.TaskDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateTaskStatusDto(
        @NotBlank(message = "required completed")
        @Pattern(regexp = "^(true|false)$", message = "must be a boolean value")
        String completed
) {
}
