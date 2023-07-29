package com.employee.selfcare.entites;

import javax.persistence.*;

@Entity
public class Projects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String projectName;

    // Other project details

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee assignedEmployee;

    // Constructors, getters, and setters

    public Projects() {
    }

    // Constructors, getters, and setters

    // Getters and setters for other properties

    public Employee getAssignedEmployee() {
        return assignedEmployee;
    }

    public void setAssignedEmployee(Employee assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }
}

