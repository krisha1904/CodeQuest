package com.codequest.repository;

import com.codequest.entity.StudentActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StudentActivityRepository extends JpaRepository<StudentActivity, Long> {
    List<StudentActivity> findTop10ByUserIdOrderByCreatedAtDesc(Long userId);
    List<StudentActivity> findTop100ByOrderByCreatedAtDesc();
    List<StudentActivity> findTop100ByActivityTypeOrderByCreatedAtDesc(String activityType);
    long countByActivityType(String activityType);
    long countByActivityTypeAndCreatedAtAfter(String activityType, LocalDateTime after);
}

