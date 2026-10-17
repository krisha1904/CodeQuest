package com.codequest.repository;

import com.codequest.entity.MistakeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MistakeRecordRepository extends JpaRepository<MistakeRecord, Long> {
    Optional<MistakeRecord> findByUserIdAndQuestionId(Long userId, Long questionId);
    List<MistakeRecord> findByUserIdOrderByLastAttemptedAtDesc(Long userId);
    List<MistakeRecord> findByUserIdAndIsResolvedFalseOrderByLastAttemptedAtDesc(Long userId);
    long countByUserIdAndIsResolvedFalse(Long userId);
}
