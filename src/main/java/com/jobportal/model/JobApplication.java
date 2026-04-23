package com.jobportal.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "job_applications")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_post_id")
    private JobPost jobPost;

    @ManyToOne
    @JoinColumn(name = "seeker_id")
    private User seeker;

    private Date appliedDate = new Date();
    
    private String status = "PENDING"; // PENDING, REVIEWED, ACCEPTED, REJECTED

    public JobApplication() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public JobPost getJobPost() { return jobPost; }
    public void setJobPost(JobPost jobPost) { this.jobPost = jobPost; }
    public User getSeeker() { return seeker; }
    public void setSeeker(User seeker) { this.seeker = seeker; }
    public Date getAppliedDate() { return appliedDate; }
    public void setAppliedDate(Date appliedDate) { this.appliedDate = appliedDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
