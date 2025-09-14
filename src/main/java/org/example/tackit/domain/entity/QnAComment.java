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
@Table(name = "qna_comment")
public class QnAComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member writer;

    @ManyToOne
    @JoinColumn(name = "qna_id", nullable = false)
    private QnAPost qnAPost;

    private String content;
    private LocalDateTime createdAt;
    private Status status;
    private int reportCount;

    public void updateContent(String content) {
        this.content = content;
    }

    public void markAsDeleted() {
        this.status = Status.DELETED;
    }

    public void increaseReportCount() {
        this.reportCount++;
    }
}
