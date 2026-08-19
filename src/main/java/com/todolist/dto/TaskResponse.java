package com.todolist.dto;

import com.todolist.domain.Task;

import java.util.UUID;

public record TaskResponse(
        UUID id,
        String text,
        boolean done,
        UUID projectId
) {
    public static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getText(),
                task.isDone(),
                task.getProject().getId()
        );
    }
}
