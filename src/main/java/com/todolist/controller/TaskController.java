package com.todolist.controller;

import com.todolist.dto.TaskRequest;
import com.todolist.dto.TaskResponse;
import com.todolist.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects/{projectId}/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<TaskResponse> findByProject(@PathVariable UUID projectId) {
        return taskService.findByProject(projectId);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@PathVariable UUID projectId, @Valid @RequestBody TaskRequest request) {
        TaskResponse created = taskService.create(projectId, request);
        return ResponseEntity.created(
                URI.create("/api/v1/projects/" + projectId + "/tasks/" + created.id())
        ).body(created);
    }

    @PatchMapping("/{taskId}/toggle")
    public TaskResponse toggleDone(@PathVariable UUID projectId, @PathVariable UUID taskId) {
        return taskService.toggleDone(projectId, taskId);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID projectId, @PathVariable UUID taskId) {
        taskService.delete(projectId, taskId);
    }
}
