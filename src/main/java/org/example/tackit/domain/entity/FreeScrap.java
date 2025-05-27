package org.example.tackit.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class FreeScrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "free_id", nullable = false)
    private FreePost freePost;

    @Column(name = "saved_at", nullable = false)
    private LocalDateTime savedAt;

    private Post type = Post.Free;

    @Column(nullable = true)
    private String tag;

    @Builder
    public FreeScrap(Member member, FreePost freePost) {
        if (member == null || freePost == null) {
            throw new IllegalArgumentException("회원 또는 게시글은 null일 수 없습니다.");
        }
        this.member = member;
        this.freePost = freePost;
        this.savedAt = LocalDateTime.now();
        this.tag = freePost.getTag();
    }


}
