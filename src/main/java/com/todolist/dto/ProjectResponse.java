package com.todolist.dto;

import com.todolist.domain.Project;

import java.time.LocalDate;
import java.util.UUID;

public record ProjectResponse(
        UUID id,
        String title,
        String description,
        LocalDate dueDate
) {
    public static ProjectResponse from(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getDueDate()
        );
    }
}
