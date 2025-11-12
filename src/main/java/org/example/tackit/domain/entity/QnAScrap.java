package org.example.tackit.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "qna_scrap")
public class QnAScrap {
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
    private LocalDateTime savedAt;

    @Column(nullable = false)
    private Post type;

    @Builder
    public QnAScrap(Member member, QnAPost qnaPost, LocalDateTime savedAt) {
        this.member = member;
        this.qnaPost = qnaPost;
        this.savedAt = savedAt;
        this.type = Post.QnA; // 여기서 기본값 명확하게 지정
    }
}
