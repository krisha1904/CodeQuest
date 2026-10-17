package com.codequest.service;

import com.codequest.dto.SocialDto;
import com.codequest.entity.*;
import com.codequest.exception.BadRequestException;
import com.codequest.exception.ResourceNotFoundException;
import com.codequest.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SocialService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private GamificationService gamificationService;

    @Transactional
    public Friendship sendFriendRequest(String senderUsername, String targetIdentifier) {
        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        User target = userRepository.findByUsername(targetIdentifier)
                .or(() -> userRepository.findByEmail(targetIdentifier))
                .orElseThrow(() -> new ResourceNotFoundException("Friend target not found"));

        if (sender.getId().equals(target.getId())) {
            throw new BadRequestException("You cannot add yourself as a friend.");
        }

        Friendship friendship = friendshipRepository.findByUserIdAndFriendId(sender.getId(), target.getId())
                .orElse(new Friendship(sender, target, "PENDING"));

        friendship.setStatus("PENDING");
        Friendship saved = friendshipRepository.save(friendship);

        Notification notification = new Notification(
                target,
                "Friend Request Received",
                sender.getFullName() + " (@" + sender.getUsername() + ") sent you a friend request.",
                "/friends.html"
        );
        notificationRepository.save(notification);

        return saved;
    }

    @Transactional
    public Friendship acceptFriendRequest(Long friendshipId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new ResourceNotFoundException("Friend request not found"));

        if (!friendship.getFriend().getId().equals(user.getId())) {
            throw new BadRequestException("Unauthorized to accept this friend request.");
        }

        friendship.setStatus("ACCEPTED");
        Friendship saved = friendshipRepository.save(friendship);

        Notification notification = new Notification(
                friendship.getUser(),
                "Friend Request Accepted",
                user.getFullName() + " accepted your friend request!",
                "/friends.html"
        );
        notificationRepository.save(notification);

        return saved;
    }

    @Transactional(readOnly = true)
    public List<Friendship> getAcceptedFriends(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return friendshipRepository.findAcceptedFriendsByUserId(user.getId());
    }

    @Transactional
    public Challenge createChallenge(String creatorUsername, SocialDto.CreateChallengeDto dto) {
        User creator = userRepository.findByUsername(creatorUsername).orElseThrow();
        User opponent = userRepository.findById(dto.getFriendId())
                .orElseThrow(() -> new ResourceNotFoundException("Friend not found"));
        Lesson lesson = lessonRepository.findById(dto.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

        Challenge challenge = new Challenge(creator, lesson);
        Challenge savedChallenge = challengeRepository.save(challenge);

        ChallengeParticipant p1 = new ChallengeParticipant(savedChallenge, creator);
        ChallengeParticipant p2 = new ChallengeParticipant(savedChallenge, opponent);

        savedChallenge.getParticipants().add(p1);
        savedChallenge.getParticipants().add(p2);
        challengeRepository.save(savedChallenge);

        Notification notification = new Notification(
                opponent,
                "Quiz Challenge Received!",
                creator.getFullName() + " challenged you to a quiz on \"" + lesson.getTitle() + "\"!",
                "/challenges.html"
        );
        notificationRepository.save(notification);

        return savedChallenge;
    }

    @Transactional
    public Challenge submitChallengeScore(Long challengeId, int score, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new ResourceNotFoundException("Challenge not found"));

        ChallengeParticipant currentParticipant = challenge.getParticipants().stream()
                .filter(p -> p.getUser().getId().equals(user.getId()))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Not a participant of this challenge"));

        currentParticipant.setScore(score);
        currentParticipant.setCompleted(true);

        boolean allCompleted = challenge.getParticipants().stream().allMatch(ChallengeParticipant::isCompleted);
        if (allCompleted && challenge.getParticipants().size() >= 2) {
            challenge.setStatus("COMPLETED");
            ChallengeParticipant p1 = challenge.getParticipants().get(0);
            ChallengeParticipant p2 = challenge.getParticipants().get(1);

            if (p1.getScore() > p2.getScore()) {
                challenge.setWinner(p1.getUser());
                gamificationService.checkAndAwardBadge(p1.getUser(), "CHALLENGE_CHAMPION");
            } else if (p2.getScore() > p1.getScore()) {
                challenge.setWinner(p2.getUser());
                gamificationService.checkAndAwardBadge(p2.getUser(), "CHALLENGE_CHAMPION");
            }
        } else {
            challenge.setStatus("IN_PROGRESS");
        }

        return challengeRepository.save(challenge);
    }
}
