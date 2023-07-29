package com.employee.selfcare.services;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.employee.selfcare.entites.EmployeeProject;
import com.employee.selfcare.repositories.EmployeeProjectRepository;

import antlr.collections.List;

@Service
public class EmployeeProjectService {
	private final EmployeeProjectRepository employeeProjectRepository;

	// Constructor injection
	public EmployeeProjectService(EmployeeProjectRepository employeeProjectRepository) {
		this.employeeProjectRepository = employeeProjectRepository;
	}

	// Implement methods to add/update/delete employee's project details

	public EmployeeProject saveOrUpdateEmployeeProject(EmployeeProject employeeProject) {
		return employeeProjectRepository.save(employeeProject);
	}

	public void deleteEmployeeProject(Long id) {
		employeeProjectRepository.deleteById(id);
	}

}