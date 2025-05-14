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
@Table(name = "qna_post")
public class QnAPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member writer;

    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Post type;

    @Column(nullable = true)
    private String tag;
    private Status status;
    private int reportCount;

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void markAsDeleted() {
        this.status = Status.DELETED;
    }

    public void increaseReportCount() {
        this.reportCount++;
        if (this.reportCount >= 3) {
            this.status = Status.DELETED;
        }
    }


}
