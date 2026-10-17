package com.codequest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "doubt_replies")
public class DoubtReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doubt_id", nullable = false)
    @JsonIgnore
    private Doubt doubt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String replyText;

    @Column(nullable = false)
    private boolean isAdminReply = false;

    private LocalDateTime createdAt = LocalDateTime.now();

    public DoubtReply() {}

    public DoubtReply(Doubt doubt, User author, String replyText, boolean isAdminReply) {
        this.doubt = doubt;
        this.author = author;
        this.replyText = replyText;
        this.isAdminReply = isAdminReply;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Doubt getDoubt() { return doubt; }
    public void setDoubt(Doubt doubt) { this.doubt = doubt; }

    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    public String getReplyText() { return replyText; }
    public void setReplyText(String replyText) { this.replyText = replyText; }

    public boolean isAdminReply() { return isAdminReply; }
    public void setAdminReply(boolean adminReply) { isAdminReply = adminReply; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
