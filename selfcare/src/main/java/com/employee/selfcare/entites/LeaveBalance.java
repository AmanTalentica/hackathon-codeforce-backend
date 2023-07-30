package com.employee.selfcare.entites;

import javax.persistence.*;

@Entity
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "emp_id")
    private Employee employee;

    private int remainingLeaves;

    // Constructors (including a no-args constructor)
    
    public LeaveBalance() {
    }

    public LeaveBalance(Employee employee, int remainingLeaves) {
        this.employee = employee;
        this.remainingLeaves = remainingLeaves;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getRemainingLeaves() {
        return remainingLeaves;
    }

    public void setRemainingLeaves(int remainingLeaves) {
        this.remainingLeaves = remainingLeaves;
    }
}
