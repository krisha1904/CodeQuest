package com.codequest.repository;

import com.codequest.entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
    List<QuizAttempt> findByUserIdOrderByAttemptedAtDesc(Long userId);
    List<QuizAttempt> findTop5ByUserIdOrderByAttemptedAtDesc(Long userId);
    List<QuizAttempt> findByUserIdAndLessonIdOrderByAttemptedAtDesc(Long userId, Long lessonId);

    @Query("SELECT AVG(q.percentage) FROM QuizAttempt q WHERE q.user.id = :userId")
    Double getAverageScoreByUserId(Long userId);

    @Query("SELECT AVG(q.percentage) FROM QuizAttempt q")
    Double getOverallPlatformAverage();
}
