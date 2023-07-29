package com.employee.selfcare.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.employee.selfcare.entites.Employee;
import com.employee.selfcare.entites.Project;

import antlr.collections.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
	
//	@Query("SELECT p FROM Project p JOIN p.employeeProjects ep JOIN ep.employee e WHERE e.empId = :empId")
//    List findAllByEmployeeId(Long empId);
}
