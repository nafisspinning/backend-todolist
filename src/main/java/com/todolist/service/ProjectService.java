package com.todolist.service;

import com.todolist.domain.Project;
import com.todolist.dto.ProjectRequest;
import com.todolist.dto.ProjectResponse;
import com.todolist.exception.ResourceNotFoundException;
import com.todolist.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<ProjectResponse> findAll() {
        return projectRepository.findAll().stream()
                .map(ProjectResponse::from)
                .toList();
    }

    public ProjectResponse findById(UUID id) {
        return ProjectResponse.from(getOrThrow(id));
    }

    @Transactional
    public ProjectResponse create(ProjectRequest request) {
        Project project = Project.builder()
                .title(request.title())
                .description(request.description())
                .dueDate(request.dueDate())
                .build();
        return ProjectResponse.from(projectRepository.save(project));
    }

    @Transactional
    public ProjectResponse update(UUID id, ProjectRequest request) {
        Project project = getOrThrow(id);
        project.setTitle(request.title());
        project.setDescription(request.description());
        project.setDueDate(request.dueDate());
        return ProjectResponse.from(project);
    }

    @Transactional
    public void delete(UUID id) {
        Project project = getOrThrow(id);
        projectRepository.delete(project);
    }

    private Project getOrThrow(UUID id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + id));
    }
}
