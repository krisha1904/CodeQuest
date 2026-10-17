package com.codequest.repository;

import com.codequest.entity.StreakRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StreakRecordRepository extends JpaRepository<StreakRecord, Long> {
    Optional<StreakRecord> findByUserIdAndActivityDate(Long userId, LocalDate activityDate);
    List<StreakRecord> findByUserIdAndActivityDateBetweenOrderByActivityDateDesc(Long userId, LocalDate startDate, LocalDate endDate);
    List<StreakRecord> findByUserIdOrderByActivityDateDesc(Long userId);
}
