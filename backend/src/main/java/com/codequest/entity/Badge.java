package com.codequest.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "badges")
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 50)
    private String iconName;

    @Column(nullable = false)
    private int xpBonus = 50;

    public Badge() {}

    public Badge(String code, String title, String description, String iconName, int xpBonus) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.iconName = iconName;
        this.xpBonus = xpBonus;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIconName() { return iconName; }
    public void setIconName(String iconName) { this.iconName = iconName; }

    public int getXpBonus() { return xpBonus; }
    public void setXpBonus(int xpBonus) { this.xpBonus = xpBonus; }
}
