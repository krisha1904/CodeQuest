package com.codequest.repository;

import com.codequest.entity.Doubt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoubtRepository extends JpaRepository<Doubt, Long> {
    List<Doubt> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<Doubt> findAllByOrderByCreatedAtDesc();
    List<Doubt> findByStatusOrderByCreatedAtDesc(String status);
    long countByStatus(String status);
}
