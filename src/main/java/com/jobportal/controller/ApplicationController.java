package com.jobportal.controller;

import com.jobportal.model.JobApplication;
import com.jobportal.model.JobPost;
import com.jobportal.model.User;
import com.jobportal.repository.JobApplicationRepository;
import com.jobportal.repository.JobPostRepository;
import com.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ApplicationController {

    @Autowired
    private JobApplicationRepository applicationRepository;

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/seeker/apply/{jobId}")
    public String applyForJob(@PathVariable Long jobId, Authentication authentication) {
        User seeker = userRepository.findByEmail(authentication.getName()).orElseThrow();
        JobPost job = jobPostRepository.findById(jobId).orElseThrow();

        JobApplication app = new JobApplication();
        app.setSeeker(seeker);
        app.setJobPost(job);
        applicationRepository.save(app);

        return "redirect:/jobs?applied=true";
    }

    @GetMapping("/seeker/dashboard")
    public String seekerDashboard(Authentication authentication, Model model) {
        User seeker = userRepository.findByEmail(authentication.getName()).orElse(null);
        if (seeker != null) {
            model.addAttribute("applications", applicationRepository.findBySeekerId(seeker.getId()));
        }
        return "dashboard-seeker";
    }

    @GetMapping("/employer/applications/{jobId}")
    public String viewApplicants(@PathVariable Long jobId, Model model) {
        model.addAttribute("applications", applicationRepository.findByJobPostId(jobId));
        return "employer-applications";
    }
}
