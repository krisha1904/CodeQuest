package com.codequest.repository;

import com.codequest.entity.StudentActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentActivityRepository extends JpaRepository<StudentActivity, Long> {
    List<StudentActivity> findTop10ByUserIdOrderByCreatedAtDesc(Long userId);
}
