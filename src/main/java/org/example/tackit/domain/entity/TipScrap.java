package org.example.tackit.domain.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class TipScrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tip_id", nullable = false)
    private TipPost tipPost;

    @Column(name = "saved_at", nullable = false)
    private LocalDateTime savedAt;

    private Post type = Post.Tip;

    @Builder
    public TipScrap(Member user, TipPost tipPost) {
        if (user == null || tipPost == null) {
            throw new IllegalArgumentException("회원 또는 게시글은 null일 수 없습니다.");
        }
        this.user = user;
        this.tipPost = tipPost;
        this.savedAt = LocalDateTime.now();
    }
}
