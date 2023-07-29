package com.employee.selfcare.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long empId;

    @Column(name = "current_designation")
    private String currentDesignation;

    @Column(name = "gender")
    private String gender;

    @Column(name = "interest_area")
    private String interestArea;

    @Column(name = "name")
    private String name;

    @Column(name = "tools")
    private String tools;

    @Column(name = "years_experience")
    private int yearsExperience;

    // Default constructor (needed by JPA)
    public Employee() {
    }

    // Parameterized constructor
    public Employee(String currentDesignation, String gender, String interestArea, String name,
                    String tools, int yearsExperience) {
        this.currentDesignation = currentDesignation;
        this.gender = gender;
        this.interestArea = interestArea;
        this.name = name;
        this.tools = tools;
        this.yearsExperience = yearsExperience;
    }

    // Getters and Setters

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getCurrentDesignation() {
        return currentDesignation;
    }

    public void setCurrentDesignation(String currentDesignation) {
        this.currentDesignation = currentDesignation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getInterestArea() {
        return interestArea;
    }

    public void setInterestArea(String interestArea) {
        this.interestArea = interestArea;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        this.tools = tools;
    }

    public int getYearsExperience() {
        return yearsExperience;
    }

    public void setYearsExperience(int yearsExperience) {
        this.yearsExperience = yearsExperience;
    }

    // toString method

    @Override
    public String toString() {
        return "Employee [empId=" + empId + ", currentDesignation=" + currentDesignation + ", gender=" + gender
                + ", interestArea=" + interestArea + ", name=" + name + ", tools=" + tools + ", yearsExperience="
                + yearsExperience + "]";
    }
}
