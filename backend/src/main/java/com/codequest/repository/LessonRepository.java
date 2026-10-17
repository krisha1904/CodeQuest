package com.codequest.repository;

import com.codequest.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByModuleIdOrderBySequenceOrderAsc(Long moduleId);
    List<Lesson> findByIsPublishedTrueOrderByModuleIdAscSequenceOrderAsc();
    long countByIsPublishedTrue();
}
