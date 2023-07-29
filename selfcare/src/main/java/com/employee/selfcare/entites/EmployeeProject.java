package com.employee.selfcare.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "employee_project")
public class EmployeeProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_emp_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_project_id")
    private Project project;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "roles")
    private String roles;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "tools_used")
    private String toolsUsed;

    // Constructors

    public EmployeeProject() {
    }

    public EmployeeProject(Employee employee, Project project, Date endDate, String roles, Date startDate, String toolsUsed) {
        this.employee = employee;
        this.project = project;
        this.endDate = endDate;
        this.roles = roles;
        this.startDate = startDate;
        this.toolsUsed = toolsUsed;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getToolsUsed() {
        return toolsUsed;
    }

    public void setToolsUsed(String toolsUsed) {
        this.toolsUsed = toolsUsed;
    }

    // toString method

    @Override
    public String toString() {
        return "EmployeeProject [id=" + id + ", employee=" + employee + ", project=" + project + ", endDate=" + endDate
                + ", roles=" + roles + ", startDate=" + startDate + ", toolsUsed=" + toolsUsed + "]";
    }
}
