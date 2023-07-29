package com.employee.selfcare.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.selfcare.entites.EmployeeAuth;

@Repository
public interface EmployeeAuthRepository extends JpaRepository<EmployeeAuth, Long> {

	EmployeeAuth findByEmailAndPassword(String email, String password);

	EmployeeAuth findByEmail(String email);

}
