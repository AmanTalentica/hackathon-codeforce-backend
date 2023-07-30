package com.employee.selfcare.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.selfcare.entites.LeaveApplied;

public interface LeaveAppliedRepository extends JpaRepository<LeaveApplied, Long> {
    // You can add custom query methods here if needed
}
