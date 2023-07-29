package com.employee.selfcare.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.selfcare.entites.Employee;
import com.employee.selfcare.entites.EmployeeAuth;
import com.employee.selfcare.entites.EmployeeProject;
import com.employee.selfcare.entites.EmployeeWithProjectsDTO;
import com.employee.selfcare.entites.Project;
import com.employee.selfcare.repositories.EmployeeRepository;
import com.employee.selfcare.repositories.ProjectRepository;
import com.employee.selfcare.services.EmployeeAuthService;
import com.employee.selfcare.services.EmployeeProjectService;
import com.employee.selfcare.services.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	private final EmployeeService employeeService;

	private final EmployeeProjectService employeeProjectService;

	private final EmployeeRepository employeeRepository;

	private final ProjectRepository projectRepository;

	private final EmployeeAuthService employeeAuthService;

	// Constructor injection
	public EmployeeController(EmployeeService employeeService, EmployeeProjectService employeeProjectService,
			ProjectRepository projectRepository, EmployeeRepository employeeRepository,
			EmployeeAuthService employeeAuthService) {
		this.employeeService = employeeService;
		this.employeeProjectService = employeeProjectService;
		this.projectRepository = projectRepository;
		this.employeeRepository = employeeRepository;
		this.employeeAuthService = employeeAuthService;
	}

	// Implement API endpoints for adding/updating/deleting employee details

	@PostMapping("/")
	public Employee addOrUpdateEmployee(@RequestBody Employee employee) {
		return employeeService.saveOrUpdateEmployee(employee);
	}

	@DeleteMapping("/{empId}")
	public ResponseEntity<String> deleteEmployee(@PathVariable Long empId) {
		boolean isDeleted = employeeService.deleteEmployee(empId);
		if (isDeleted) {
			return ResponseEntity.ok("Employee with empId " + empId + " deleted successfully.");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Implement API endpoints for searching employees

	@GetMapping("/search")
	public List<Employee> searchEmployeesByTool(@RequestParam("tool") String tool) {
		return employeeService.searchEmployeesByTool(tool);
	}

	@GetMapping("/{employeeId}/projects")
	public ResponseEntity<List<EmployeeProject>> getProjectsByEmployeeId(@PathVariable Long employeeId) {
		Employee employee = employeeService.getEmployeeById(employeeId);
		if (employee != null) {
			List<EmployeeProject> projects = employeeService.getProjectsByEmployee(employee);
			return ResponseEntity.ok(projects);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/all")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> employees = employeeService.getAllEmployees();
		if (!employees.isEmpty()) {
			return ResponseEntity.ok(employees);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/authenticate")
	public ResponseEntity<Employee> authenticateEmployee(@RequestBody EmployeeAuth employeeAuth) {
		String email = employeeAuth.getEmail();
		String password = employeeAuth.getPassword();

		EmployeeAuth authenticatedEmployee = employeeAuthService.authenticate(email, password);

		if (authenticatedEmployee != null) {
			Long empId = authenticatedEmployee.getEmpId();
			Employee employee = employeeService.getEmployeeById(empId);

			return ResponseEntity.ok(employee);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// API endpoint for employee registration
	@PostMapping("/register")
	public ResponseEntity<String> registerEmployee(@RequestBody EmployeeAuth employeeAuth) {
		EmployeeAuth savedEmployeeAuth = employeeAuthService.registerEmployee(employeeAuth);
		if (savedEmployeeAuth != null) {
			return ResponseEntity.ok("Registration successful!");
		} else {
			return ResponseEntity.badRequest().body("Registration failed. Please try again.");
		}
	}
	
	 @GetMapping("/searchs")
	    public ResponseEntity<List<Employee>> getEmployees(
	            @RequestParam(required = false) Long id,
	            @RequestParam(required = false) String name,
	            @RequestParam(required = false) String currentDesignation,
	            @RequestParam(required = false) Integer yearsExperience
	    ) {
	        List<Employee> employees = employeeService.getEmployeesByCriteria(id, name, currentDesignation, yearsExperience);
	        if (!employees.isEmpty()) {
	            return ResponseEntity.ok(employees);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

//	 @PutMapping("/{empId}/projects/{projectId}")
//	    public ResponseEntity<Employee> updateEmployeeProject(
//	            @PathVariable Long empId,
//	            @PathVariable Long projectId,
//	            @RequestBody EmployeeProject updatedProject
//	    ) {
//	        Employee updatedEmployee = employeeService.updateEmployeeProject(empId, projectId, updatedProject);
//	        if (updatedEmployee != null) {
//	            return ResponseEntity.ok(updatedEmployee);
//	        } else {
//	            return ResponseEntity.notFound().build();
//	        }
//	    }
}
