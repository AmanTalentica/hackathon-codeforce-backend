package com.employee.selfcare.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.selfcare.entites.EmployeeProject;
import com.employee.selfcare.services.EmployeeProjectService;

@RestController
@RequestMapping("/api/employee-projects")
public class EmployeeProjectController {
    private final EmployeeProjectService employeeProjectService;
    
    // Constructor injection
    public EmployeeProjectController(EmployeeProjectService employeeProjectService) {
        this.employeeProjectService = employeeProjectService;
    }
    
    // Implement API endpoints for adding/updating/deleting employee's project details
    
    @PostMapping("/")
    public EmployeeProject addOrUpdateEmployeeProject(@RequestBody EmployeeProject employeeProject) {
        return employeeProjectService.saveOrUpdateEmployeeProject(employeeProject);
    }
    
    @DeleteMapping("/{id}")
    public void deleteEmployeeProject(@PathVariable Long id) {
        employeeProjectService.deleteEmployeeProject(id);
    }
}
