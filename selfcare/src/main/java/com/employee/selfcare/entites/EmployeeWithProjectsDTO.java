package com.employee.selfcare.entites;

import java.util.List;

import com.employee.selfcare.entites.ChatGptResponse.Choice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeWithProjectsDTO {
    private Employee employee;
    //private List<Project> projects;
    private List<EmployeeProject> projects;

    // Constructors, getters, and setters...
}