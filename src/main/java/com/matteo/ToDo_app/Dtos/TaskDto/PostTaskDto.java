package com.matteo.ToDo_app.Dtos.TaskDto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record PostTaskDto (
        @NotBlank(message = "required description")
        @Length(min = 6, max = 255,message = "description lenght must be between 6 and 255 characteres")
        String description
){
}
