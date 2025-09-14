package org.example.tackit.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "qna_report")
public class QnAReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_post_id", nullable = false)
    private QnAPost qnaPost;

    @Column(nullable = false)
    private LocalDateTime reportedAt;

    @Column(nullable = false)
    private Post type;

    @Builder
    public QnAReport(Member member, QnAPost qnaPost) {
        this.member = member;
        this.qnaPost = qnaPost;
        this.reportedAt = LocalDateTime.now();
        this.type = Post.QnA;
    }
}
