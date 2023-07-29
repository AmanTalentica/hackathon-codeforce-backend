package com.employee.selfcare.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.employee.selfcare.entites.Employee;
import com.employee.selfcare.entites.EmployeeProject;
import com.employee.selfcare.repositories.EmployeeProjectRepository;
import com.employee.selfcare.repositories.EmployeeRepository;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    
    private final EmployeeProjectRepository employeeProjectRepository;
    
    
    
    // Constructor injection
    public EmployeeService(EmployeeRepository employeeRepository,EmployeeProjectRepository employeeProjectRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeProjectRepository =employeeProjectRepository;
    }
    
    // Implement methods to add/update/delete employee details
    
    public Employee saveOrUpdateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    public boolean deleteEmployee(Long empId) {
        // Check if the employee exists
        Employee employee = employeeRepository.findById(empId).orElse(null);

        if (employee != null) {
            employeeRepository.delete(employee);
            return true;
        } else {
            return false; // Employee with empId not found
        }
    }
    
    // Implement methods to search employees
    
    public List<Employee> searchEmployeesByTool(String tool) {
        // Custom query to find employees by tool
        return employeeRepository.findByToolsContaining(tool);
    }
    
    public Employee getEmployeeById(Long empId) {
        return employeeRepository.findById(empId).orElse(null);
    }

    public List<EmployeeProject> getProjectsByEmployee(Employee employee) {
        return employeeProjectRepository.findProjectsByEmployee(employee);
    }
    
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> getEmployeesByCriteria(Long id, String name, String currentDesignation, Integer yearsExperience) {
        return employeeRepository.findByCriteria(id, name, currentDesignation, yearsExperience);
    }

//    public Employee updateEmployeeProject(Long empId, Long projectId, EmployeeProject updatedProject) {
//        Employee employee = employeeRepository.findById(empId).orElse(null);
//
//        if (employee != null && updatedProject != null) {
//            List<EmployeeProject> employeeProjects = employee.getEmployeeProjects();
//            if (employeeProjects != null) {
//                for (EmployeeProject project : employeeProjects) {
//                    if (project.getId().equals(projectId)) {
//                        // Update the project details
//                        project.setEndDate(updatedProject.getEndDate());
//                        project.setRoles(updatedProject.getRoles());
//                        project.setStartDate(updatedProject.getStartDate());
//                        project.setToolsUsed(updatedProject.getToolsUsed());
//
//                        return employeeRepository.save(employee);
//                    }
//                }
//            }
//        }
//
//        return null; // Employee or project not found
//    }
    
}


