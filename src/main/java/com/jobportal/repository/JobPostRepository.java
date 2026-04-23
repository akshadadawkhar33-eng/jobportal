package com.jobportal.repository;

import com.jobportal.model.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    List<JobPost> findByEmployerId(Long employerId);
    List<JobPost> findByStatus(String status);
    List<JobPost> findByTitleContainingIgnoreCaseAndStatusOrDescriptionContainingIgnoreCaseAndStatus(String title, String status1, String desc, String status2);
    long countByStatus(String status);
}
