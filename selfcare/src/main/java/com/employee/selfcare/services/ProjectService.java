package com.employee.selfcare.services;

import org.springframework.stereotype.Service;

import com.employee.selfcare.entites.Project;
import com.employee.selfcare.repositories.ProjectRepository;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    
    // Constructor injection
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    
    // Implement methods to add/update/delete project details
    
    public Project saveOrUpdateProject(Project project) {
        return projectRepository.save(project);
    }
    
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }
}

