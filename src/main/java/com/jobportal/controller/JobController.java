package com.jobportal.controller;

import com.jobportal.model.JobPost;
import com.jobportal.model.User;
import com.jobportal.repository.JobPostRepository;
import com.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class JobController {

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/jobs")
    public String listAllJobs(@RequestParam(required=false) String keyword, Model model) {
        List<JobPost> jobs;
        if(keyword != null && !keyword.isEmpty()) {
            jobs = jobPostRepository.findByTitleContainingIgnoreCaseAndStatusOrDescriptionContainingIgnoreCaseAndStatus(keyword, "APPROVED", keyword, "APPROVED");
        } else {
            jobs = jobPostRepository.findByStatus("APPROVED");
        }
        model.addAttribute("jobs", jobs);
        model.addAttribute("keyword", keyword);
        return "jobs";
    }

    @GetMapping("/employer/dashboard")
    public String employerDashboard(Authentication authentication, Model model) {
        User employer = userRepository.findByEmail(authentication.getName()).orElse(null);
        if (employer != null) {
            model.addAttribute("jobs", jobPostRepository.findByEmployerId(employer.getId()));
        }
        return "dashboard-employer";
    }

    @GetMapping("/employer/job/new")
    public String newJobForm(Model model) {
        model.addAttribute("jobPost", new JobPost());
        return "job-form";
    }

    @PostMapping("/employer/job/post")
    public String saveJob(@ModelAttribute("jobPost") JobPost jobPost, Authentication authentication) {
        User employer = userRepository.findByEmail(authentication.getName()).orElseThrow();
        jobPost.setEmployer(employer);
        jobPost.setStatus("PENDING");
        jobPostRepository.save(jobPost);
        return "redirect:/employer/dashboard?posted=true";
    }
}
