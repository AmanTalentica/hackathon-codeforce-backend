package com.employee.selfcare.services;

import org.springframework.stereotype.Service;

import com.employee.selfcare.entites.Employee;
import com.employee.selfcare.entites.EmployeeAuth;
import com.employee.selfcare.entites.EmployeeWithProjectsDTO;
import com.employee.selfcare.repositories.EmployeeAuthRepository;
import com.employee.selfcare.repositories.EmployeeRepository;

@Service
public class EmployeeAuthService {
	private final EmployeeAuthRepository employeeAuthRepository;
	private final EmployeeService employeeService;
	private final EmployeeRepository employeeRepository;

	// Constructor injection
	public EmployeeAuthService(EmployeeAuthRepository employeeAuthRepository, EmployeeService employeeService,
			EmployeeRepository employeeRepository) {
		this.employeeAuthRepository = employeeAuthRepository;
		this.employeeService = employeeService;
		this.employeeRepository = employeeRepository;
	}

	// Method for employee authentication
	public EmployeeAuth authenticate(String email, String password) {
		EmployeeAuth employeeAuth = employeeAuthRepository.findByEmail(email);
		if (employeeAuth != null && employeeAuth.getPassword().equals(password)) {
			return employeeAuth;
		} else {
			return null;
		}
	}

	// Method for employee registration
	public EmployeeAuth registerEmployee(EmployeeAuth employeeAuth) {
		// You may want to add more validation logic here before saving the employeeAuth
		return employeeAuthRepository.save(employeeAuth);
	}
}
