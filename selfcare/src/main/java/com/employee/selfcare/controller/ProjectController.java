package com.employee.selfcare.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.selfcare.entites.Project;
import com.employee.selfcare.services.ProjectService;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;
    
    // Constructor injection
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    
    // Implement API endpoints for adding/updating/deleting project details
    
    @PostMapping("/")
    public Project addOrUpdateProject(@RequestBody Project project) {
        return projectService.saveOrUpdateProject(project);
    }
    
    @DeleteMapping("/{projectId}")
    public void deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
    }
}