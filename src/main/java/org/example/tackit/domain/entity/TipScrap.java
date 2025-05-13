package org.example.tackit.domain.entity;


import jakarta.persistence.*;
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

    private LocalDateTime saved_at;

    @Enumerated(EnumType.STRING)
    private Post type = Post.Tip;

    public TipScrap(Member member, TipPost tipPost) {
        this.member = member;
        this.tipPost = tipPost;
        this.saved_at = LocalDateTime.now();
    }
}
