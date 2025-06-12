package org.example.tackit.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tip_report")
public class TipReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tip_post_id", nullable = false)
    private TipPost tipPost;

    @Column(nullable = false)
    private LocalDateTime reportedAt;

    @Column(nullable = false)
    private Post type;

    @Builder
    public TipReport(Member member, TipPost tipPost) {
        this.member = member;
        this.tipPost = tipPost;
        this.reportedAt = LocalDateTime.now();
        this.type = Post.Tip;
    }
}
