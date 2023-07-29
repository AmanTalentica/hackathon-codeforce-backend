package com.employee.selfcare.entites;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EmployeeAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empId;
    private String email;
    private String password;
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public EmployeeAuth() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "EmployeeAuth [empId=" + empId + ", email=" + email + ", password=" + password + "]";
	}

    // Constructors, getters, and setters
}