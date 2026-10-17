package com.codequest.repository;

import com.codequest.entity.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {
    Optional<LessonProgress> findByUserIdAndLessonId(Long userId, Long lessonId);
    List<LessonProgress> findByUserIdAndIsCompletedTrue(Long userId);
    long countByUserIdAndIsCompletedTrue(Long userId);
    List<LessonProgress> findByUserId(Long userId);
}
