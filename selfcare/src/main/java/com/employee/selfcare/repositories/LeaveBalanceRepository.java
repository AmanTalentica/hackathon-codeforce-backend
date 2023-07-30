package com.employee.selfcare.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.employee.selfcare.entites.Employee;
import com.employee.selfcare.entites.LeaveBalance;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {
	//LeaveBalance findByEmployee(Employee employee);
	
	@Query(value = "SELECT * FROM leave_balance WHERE employee_id = :employeeId", nativeQuery = true)
    LeaveBalance findByEmployeeNative(Long employeeId);

}
