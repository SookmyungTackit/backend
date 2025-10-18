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
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tip_id", nullable = false)
    private TipPost tipPost;

    @Column(name = "saved_at", nullable = false)
    private LocalDateTime savedAt;

    private Post type = Post.Tip;

    @Builder
    public TipScrap(Member member, TipPost tipPost, LocalDateTime savedAt) {
        this.member = member;
        this.tipPost = tipPost;
        this.savedAt = LocalDateTime.now();
    }
}
