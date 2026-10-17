package com.codequest.repository;

import com.codequest.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByLessonId(Long lessonId);
    long countByLessonId(Long lessonId);
}
