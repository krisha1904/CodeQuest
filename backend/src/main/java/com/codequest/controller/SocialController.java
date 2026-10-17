package com.codequest.controller;

import com.codequest.dto.SocialDto;
import com.codequest.entity.Challenge;
import com.codequest.entity.Friendship;
import com.codequest.entity.User;
import com.codequest.repository.ChallengeRepository;
import com.codequest.repository.UserRepository;
import com.codequest.service.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SocialController {

    @Autowired
    private SocialService socialService;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/friends")
    public ResponseEntity<?> getFriends(Authentication authentication) {
        List<Friendship> friends = socialService.getAcceptedFriends(authentication.getName());
        String currentUsername = authentication.getName();

        List<Map<String, Object>> response = friends.stream().map(f -> {
            User peer = f.getUser().getUsername().equals(currentUsername) ? f.getFriend() : f.getUser();
            Map<String, Object> map = new HashMap<>();
            map.put("friendshipId", f.getId());
            map.put("friendId", peer.getId());
            map.put("username", peer.getUsername());
            map.put("fullName", peer.getFullName());
            map.put("levelTitle", peer.getLevelTitle());
            map.put("totalPoints", peer.getTotalPoints());
            map.put("currentStreak", peer.getCurrentStreak());
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/friends/request")
    public ResponseEntity<?> sendFriendRequest(@RequestBody SocialDto.FriendRequestDto dto, Authentication authentication) {
        Friendship friendship = socialService.sendFriendRequest(authentication.getName(), dto.getTargetUsernameOrEmail());
        return ResponseEntity.ok(Map.of("success", true, "message", "Friend request sent!"));
    }

    @PutMapping("/friends/{id}/accept")
    public ResponseEntity<?> acceptFriendRequest(@PathVariable Long id, Authentication authentication) {
        socialService.acceptFriendRequest(id, authentication.getName());
        return ResponseEntity.ok(Map.of("success", true, "message", "Friend request accepted!"));
    }

    @GetMapping("/challenges")
    public ResponseEntity<?> getChallenges(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow();
        List<Challenge> challenges = challengeRepository.findChallengesByUserId(user.getId());

        List<Map<String, Object>> result = challenges.stream().map(c -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", c.getId());
            map.put("lessonId", c.getLesson().getId());
            map.put("lessonTitle", c.getLesson().getTitle());
            map.put("creatorName", c.getCreator().getFullName());
            map.put("status", c.getStatus());
            map.put("winnerName", c.getWinner() != null ? c.getWinner().getFullName() : null);
            map.put("createdAt", c.getCreatedAt());
            map.put("participants", c.getParticipants().stream().map(p -> Map.of(
                    "userId", p.getUser().getId(),
                    "fullName", p.getUser().getFullName(),
                    "score", p.getScore(),
                    "completed", p.isCompleted()
            )).collect(Collectors.toList()));
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/challenges")
    public ResponseEntity<?> createChallenge(@RequestBody SocialDto.CreateChallengeDto dto, Authentication authentication) {
        Challenge challenge = socialService.createChallenge(authentication.getName(), dto);
        return ResponseEntity.ok(Map.of("success", true, "challengeId", challenge.getId()));
    }

    @PostMapping("/challenges/{id}/submit")
    public ResponseEntity<?> submitScore(
            @PathVariable Long id,
            @RequestBody SocialDto.ChallengeScoreSubmissionDto dto,
            Authentication authentication) {
        Challenge challenge = socialService.submitChallengeScore(id, dto.getScore(), authentication.getName());
        return ResponseEntity.ok(Map.of("success", true, "status", challenge.getStatus()));
    }
}
