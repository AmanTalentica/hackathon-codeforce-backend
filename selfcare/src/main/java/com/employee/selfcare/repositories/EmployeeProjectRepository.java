package com.employee.selfcare.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.employee.selfcare.entites.Employee;
import com.employee.selfcare.entites.EmployeeProject;

@Repository
public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, Long> {

    @Query("SELECT ep FROM EmployeeProject ep WHERE ep.employee = :employee")
    List<EmployeeProject> findProjectsByEmployee(Employee employee);
    
    
}
