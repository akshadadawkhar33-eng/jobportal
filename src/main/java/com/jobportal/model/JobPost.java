package com.jobportal.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "job_posts")
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    
    @Column(columnDefinition="TEXT")
    private String description;
    
    private String location;
    private String salary;
    
    private Date postedDate = new Date();

    private String status = "PENDING"; // Auto-set for admin moderation

    @ManyToOne
    @JoinColumn(name = "employer_id", nullable = false)
    private User employer;

    public JobPost() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }
    public Date getPostedDate() { return postedDate; }
    public void setPostedDate(Date postedDate) { this.postedDate = postedDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public User getEmployer() { return employer; }
    public void setEmployer(User employer) { this.employer = employer; }
}
