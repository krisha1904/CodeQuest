package com.codequest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    @JsonIgnore
    private CourseModule module;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false)
    private int sequenceOrder;

    @Column(nullable = false)
    private int estimatedMinutes = 15;

    // 1. Definition
    @Column(nullable = false, columnDefinition = "TEXT")
    private String definition;

    // 2. Concept Explanation
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String conceptExplanation;

    // 3. Syntax
    @Column(nullable = false, columnDefinition = "TEXT")
    private String syntaxBlock;

    // 4. Java Program
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String javaProgram;

    // 5. Output
    @Column(nullable = false, columnDefinition = "TEXT")
    private String programOutput;

    // 6. Explanation of Program
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String programExplanation;

    // 7. Important Points
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String importantPoints;

    // 8. Common Mistakes
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String commonMistakes;

    // 9. Practice Questions
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String practiceQuestions;

    @Column(nullable = false)
    private boolean isPublished = true;

    private LocalDateTime createdAt = LocalDateTime.now();

    // 10. Multiple-Choice Questions
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    public Lesson() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CourseModule getModule() { return module; }
    public void setModule(CourseModule module) { this.module = module; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getSequenceOrder() { return sequenceOrder; }
    public void setSequenceOrder(int sequenceOrder) { this.sequenceOrder = sequenceOrder; }

    public int getEstimatedMinutes() { return estimatedMinutes; }
    public void setEstimatedMinutes(int estimatedMinutes) { this.estimatedMinutes = estimatedMinutes; }

    public String getDefinition() { return definition; }
    public void setDefinition(String definition) { this.definition = definition; }

    public String getConceptExplanation() { return conceptExplanation; }
    public void setConceptExplanation(String conceptExplanation) { this.conceptExplanation = conceptExplanation; }

    public String getSyntaxBlock() { return syntaxBlock; }
    public void setSyntaxBlock(String syntaxBlock) { this.syntaxBlock = syntaxBlock; }

    public String getJavaProgram() { return javaProgram; }
    public void setJavaProgram(String javaProgram) { this.javaProgram = javaProgram; }

    public String getProgramOutput() { return programOutput; }
    public void setProgramOutput(String programOutput) { this.programOutput = programOutput; }

    public String getProgramExplanation() { return programExplanation; }
    public void setProgramExplanation(String programExplanation) { this.programExplanation = programExplanation; }

    public String getImportantPoints() { return importantPoints; }
    public void setImportantPoints(String importantPoints) { this.importantPoints = importantPoints; }

    public String getCommonMistakes() { return commonMistakes; }
    public void setCommonMistakes(String commonMistakes) { this.commonMistakes = commonMistakes; }

    public String getPracticeQuestions() { return practiceQuestions; }
    public void setPracticeQuestions(String practiceQuestions) { this.practiceQuestions = practiceQuestions; }

    public boolean isPublished() { return isPublished; }
    public void setPublished(boolean published) { isPublished = published; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }
}
