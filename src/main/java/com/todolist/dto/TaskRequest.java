package com.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskRequest(

        @NotBlank(message = "text must not be blank")
        @Size(max = 500, message = "text must be at most 500 characters")
        String text
) {
}
