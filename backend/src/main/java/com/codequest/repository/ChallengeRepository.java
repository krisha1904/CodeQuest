package com.codequest.repository;

import com.codequest.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    @Query("SELECT DISTINCT c FROM Challenge c JOIN c.participants p WHERE p.user.id = :userId ORDER BY c.createdAt DESC")
    List<Challenge> findChallengesByUserId(Long userId);

    List<Challenge> findByStatusOrderByCreatedAtDesc(String status);
}
