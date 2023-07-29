package com.employee.selfcare.entites;

import com.employee.selfcare.entites.Employee;
import com.employee.selfcare.entites.EmployeeProject;

import java.util.List;



	public class AuthResponse {
	    private Employee employee;
	    private List<EmployeeProject> employeeProjects;

	    public AuthResponse() {
	    }

	    public AuthResponse(Employee employee, List<EmployeeProject> employeeProjects) {
	        this.employee = employee;
	        this.employeeProjects = employeeProjects;
	    }

	    public Employee getEmployee() {
	        return employee;
	    }

	    public void setEmployee(Employee employee) {
	        this.employee = employee;
	    }

	    public List<EmployeeProject> getEmployeeProjects() {
	        return employeeProjects;
	    }

	    public void setEmployeeProjects(List<EmployeeProject> employeeProjects) {
	        this.employeeProjects = employeeProjects;
	    }

		@Override
		public String toString() {
			return "AuthResponse [employee=" + employee + ", employeeProjects=" + employeeProjects + "]";
		}
	

}
