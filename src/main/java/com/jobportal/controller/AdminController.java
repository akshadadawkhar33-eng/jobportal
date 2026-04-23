package com.jobportal.controller;

import com.jobportal.model.JobPost;
import com.jobportal.model.Role;
import com.jobportal.repository.JobApplicationRepository;
import com.jobportal.repository.JobPostRepository;
import com.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private JobApplicationRepository applicationRepository;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("totalSeekers", userRepository.countByRole(Role.SEEKER));
        model.addAttribute("totalEmployers", userRepository.countByRole(Role.EMPLOYER));
        model.addAttribute("pendingJobs", jobPostRepository.countByStatus("PENDING"));
        model.addAttribute("approvedJobs", jobPostRepository.countByStatus("APPROVED"));
        model.addAttribute("totalApplications", applicationRepository.count());
        
        model.addAttribute("users", userRepository.findByRoleNot(Role.ADMIN));
        model.addAttribute("jobs", jobPostRepository.findAll());

        return "dashboard-admin";
    }

    @PostMapping("/job/approve/{id}")
    public String approveJob(@PathVariable Long id) {
        JobPost job = jobPostRepository.findById(id).orElseThrow();
        job.setStatus("APPROVED");
        jobPostRepository.save(job);
        return "redirect:/admin/dashboard?approved=true";
    }

    @PostMapping("/job/delete/{id}")
    public String deleteJob(@PathVariable Long id) {
        // Warning: This ignores orphaned job applications for simplicity in MVP.
        try {
            jobPostRepository.deleteById(id);
            return "redirect:/admin/dashboard?deleted=true";
        } catch(Exception e) {
            // If applications exist, soft reject instead for safety
            JobPost job = jobPostRepository.findById(id).orElseThrow();
            job.setStatus("REJECTED");
            jobPostRepository.save(job);
            return "redirect:/admin/dashboard?rejected=true";
        }
    }

    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/dashboard?deleted=true";
    }
}
