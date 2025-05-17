package org.example.tackit.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "free_comment")
public class FreeComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member writer;

    @ManyToOne
    @JoinColumn(name = "free_id", nullable = false)
    // 게시글 Id
    private FreePost freePost;

    private String content;
    private LocalDateTime createdAt;
    private int reportCount;

    public void updateContent(String content) { this.content = content; }
    public void increaseReportCount() {
        this.reportCount++;
    }
}
