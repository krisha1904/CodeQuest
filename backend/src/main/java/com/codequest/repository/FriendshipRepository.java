package com.codequest.repository;

import com.codequest.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    Optional<Friendship> findByUserIdAndFriendId(Long userId, Long friendId);

    @Query("SELECT f FROM Friendship f WHERE (f.user.id = :userId OR f.friend.id = :userId) AND f.status = 'ACCEPTED'")
    List<Friendship> findAcceptedFriendsByUserId(Long userId);

    @Query("SELECT f FROM Friendship f WHERE f.friend.id = :userId AND f.status = 'PENDING'")
    List<Friendship> findPendingRequestsForUser(Long userId);
}
