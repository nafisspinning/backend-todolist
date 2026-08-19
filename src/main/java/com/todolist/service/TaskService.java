package com.todolist.service;

import com.todolist.domain.Project;
import com.todolist.domain.Task;
import com.todolist.dto.TaskRequest;
import com.todolist.dto.TaskResponse;
import com.todolist.exception.ResourceNotFoundException;
import com.todolist.repository.ProjectRepository;
import com.todolist.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public List<TaskResponse> findByProject(UUID projectId) {
        ensureProjectExists(projectId);
        return taskRepository.findByProjectIdOrderByCreatedAtDesc(projectId).stream()
                .map(TaskResponse::from)
                .toList();
    }

    @Transactional
    public TaskResponse create(UUID projectId, TaskRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + projectId));

        Task task = Task.builder()
                .text(request.text())
                .project(project)
                .done(false)
                .build();

        return TaskResponse.from(taskRepository.save(task));
    }

    @Transactional
    public TaskResponse toggleDone(UUID projectId, UUID taskId) {
        Task task = getOrThrow(projectId, taskId);
        task.setDone(!task.isDone());
        return TaskResponse.from(task);
    }

    @Transactional
    public void delete(UUID projectId, UUID taskId) {
        Task task = getOrThrow(projectId, taskId);
        taskRepository.delete(task);
    }

    private Task getOrThrow(UUID projectId, UUID taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found: " + taskId));
        if (!task.getProject().getId().equals(projectId)) {
            throw new ResourceNotFoundException("Task " + taskId + " does not belong to project " + projectId);
        }
        return task;
    }

    private void ensureProjectExists(UUID projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project not found: " + projectId);
        }
    }
}
