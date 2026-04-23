package com.jobportal.repository;

import com.jobportal.model.Role;
import com.jobportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    long countByRole(Role role);
    List<User> findByRoleNot(Role role);
}
