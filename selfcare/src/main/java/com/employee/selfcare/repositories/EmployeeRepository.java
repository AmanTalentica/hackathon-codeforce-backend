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

	Employee findByEmpId(Long empId);

    Employee findByName(String name);

    Employee findByCurrentDesignation(String currentDesignation);

    Employee findByInterestArea(String interestArea);

    Employee findByTools(String tools);

    Employee findByYearsExperience(Integer yearsExperience);

	@Query("SELECT e FROM Employee e WHERE (:idFilter is null OR e.empId = :idFilter)" +
           " AND (:nameFilter is null OR e.name = :nameFilter)" +
           " AND (:currentDesignationFilter is null OR e.currentDesignation = :currentDesignationFilter)" +
           " AND (:interestAreaFilter is null OR e.interestArea = :interestAreaFilter)" +
           " AND (:toolsFilter is null OR e.tools = :toolsFilter)" +
           " AND (:yearsExperienceFilter is null OR e.yearsExperience = :yearsExperienceFilter)")
    List<Employee> findAllByFilters(Long idFilter, String nameFilter, String currentDesignationFilter,
                                    String interestAreaFilter, String toolsFilter, Integer yearsExperienceFilter);




}
