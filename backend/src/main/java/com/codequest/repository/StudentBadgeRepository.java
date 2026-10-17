package com.codequest.repository;

import com.codequest.entity.StudentBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentBadgeRepository extends JpaRepository<StudentBadge, Long> {
    List<StudentBadge> findByUserIdOrderByEarnedAtDesc(Long userId);
    boolean existsByUserIdAndBadgeId(Long userId, Long badgeId);
}
