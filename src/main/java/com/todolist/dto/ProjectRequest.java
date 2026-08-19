package com.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ProjectRequest(

        @NotBlank(message = "title must not be blank")
        @Size(max = 150, message = "title must be at most 150 characters")
        String title,

        @NotBlank(message = "description must not be blank")
        String description,

        @NotNull(message = "dueDate is required")
        LocalDate dueDate
) {
}
