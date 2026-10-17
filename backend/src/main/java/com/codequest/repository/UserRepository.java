package com.codequest.repository;

import com.codequest.entity.Role;
import com.codequest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    List<User> findTop10ByOrderByTotalPointsDesc();
    List<User> findTop10ByOrderByCurrentStreakDesc();
    List<User> findByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCase(String username, String fullName);

    long countByRole(Role role);
    boolean existsByRole(Role role);
    List<User> findByRole(Role role);
    List<User> findByRoleOrderByCurrentStreakDesc(Role role);
    List<User> findByRoleOrderByTotalPointsDesc(Role role);
}
