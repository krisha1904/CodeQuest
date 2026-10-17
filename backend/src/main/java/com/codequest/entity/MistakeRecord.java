package com.codequest.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mistakes", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "question_id"})
})
public class MistakeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(nullable = false, length = 1)
    private String userAnswer;

    @Column(nullable = false)
    private boolean isResolved = false;

    @Column(nullable = false)
    private int attemptsCount = 1;

    private LocalDateTime lastAttemptedAt = LocalDateTime.now();

    public MistakeRecord() {}

    public MistakeRecord(User user, Question question, String userAnswer) {
        this.user = user;
        this.question = question;
        this.userAnswer = userAnswer;
        this.isResolved = false;
        this.attemptsCount = 1;
        this.lastAttemptedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }

    public String getUserAnswer() { return userAnswer; }
    public void setUserAnswer(String userAnswer) { this.userAnswer = userAnswer; }

    public boolean isResolved() { return isResolved; }
    public void setResolved(boolean resolved) { isResolved = resolved; }

    public int getAttemptsCount() { return attemptsCount; }
    public void setAttemptsCount(int attemptsCount) { this.attemptsCount = attemptsCount; }

    public LocalDateTime getLastAttemptedAt() { return lastAttemptedAt; }
    public void setLastAttemptedAt(LocalDateTime lastAttemptedAt) { this.lastAttemptedAt = lastAttemptedAt; }

    public void incrementAttempts(String newAnswer) {
        this.attemptsCount++;
        this.userAnswer = newAnswer;
        this.lastAttemptedAt = LocalDateTime.now();
    }
}
