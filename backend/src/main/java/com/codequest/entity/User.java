package com.codequest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @JsonIgnore
    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(length = 150)
    private String collegeName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Role role = Role.ROLE_STUDENT;

    @Column(nullable = false)
    private int currentStreak = 0;

    @Column(nullable = false)
    private int longestStreak = 0;

    @Column(nullable = false)
    private int totalPoints = 0;

    @Column(nullable = false, length = 50)
    private String levelTitle = "Java Novice";

    @Column(length = 50)
    private String timezone = "Asia/Kolkata";

    private LocalDateTime lastLoginAt;

    @Column(nullable = false)
    private int loginCount = 0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    public User() {}

    public User(String username, String email, String passwordHash, String fullName, String collegeName, Role role) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.collegeName = collegeName;
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getCollegeName() { return collegeName; }
    public void setCollegeName(String collegeName) { this.collegeName = collegeName; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public int getCurrentStreak() { return currentStreak; }
    public void setCurrentStreak(int currentStreak) { this.currentStreak = currentStreak; }

    public int getLongestStreak() { return longestStreak; }
    public void setLongestStreak(int longestStreak) { this.longestStreak = longestStreak; }

    public int getTotalPoints() { return totalPoints; }
    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
        updateLevelTitle();
    }

    public String getLevelTitle() { return levelTitle; }
    public void setLevelTitle(String levelTitle) { this.levelTitle = levelTitle; }

    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getLastLoginAt() { return lastLoginAt; }
    public void setLastLoginAt(LocalDateTime lastLoginAt) { this.lastLoginAt = lastLoginAt; }

    public int getLoginCount() { return loginCount; }
    public void setLoginCount(int loginCount) { this.loginCount = loginCount; }

    public void incrementLoginCount() { this.loginCount++; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public void addPoints(int points) {
        this.totalPoints += points;
        updateLevelTitle();
    }

    private void updateLevelTitle() {
        if (this.totalPoints >= 3000) {
            this.levelTitle = "CodeQuest Master";
        } else if (this.totalPoints >= 1800) {
            this.levelTitle = "Java Champion";
        } else if (this.totalPoints >= 1000) {
            this.levelTitle = "Java Explorer";
        } else if (this.totalPoints >= 500) {
            this.levelTitle = "Code Apprentice";
        } else {
            this.levelTitle = "Java Novice";
        }
    }
}
