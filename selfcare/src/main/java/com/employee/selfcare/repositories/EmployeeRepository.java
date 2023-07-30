package com.employee.selfcare.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.employee.selfcare.entites.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	List<Employee> findByToolsContaining(String tool);

	@Query("SELECT e FROM Employee e " +
            "WHERE (:id IS NULL OR e.empId = :id) " +
            "AND (:name IS NULL OR e.name = :name) " +
            "AND (:currentDesignation IS NULL OR e.currentDesignation = :currentDesignation) " +
            "AND (:yearsExperience IS NULL OR e.yearsExperience = :yearsExperience)")
    List<Employee> findByCriteria(
            Long id,
            String name,
            String currentDesignation,
            Integer yearsExperience
    );

	Employee findByEmpId(long employeeId);
	
	 @Query(value = "SELECT * FROM employee WHERE emp_id = :empId", nativeQuery = true)
	    Employee findByEmpIdNative(String empId);
	

}
