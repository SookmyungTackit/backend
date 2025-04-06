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
public class FreePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    private String title;
    private String content;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private Post type = Post.Free;

    @Column(nullable = true)
    private String tag;

    @Builder.Default
    private Status status = Status.ACTIVE;

    @Builder.Default
    private int reportCount = 0;

    public void update(String title, String content, String tag) {
        this.title = title;
        this.content = content;

        if (tag == null || tag.trim().isEmpty()) {
            this.tag = null; // 태그 삭제
        } else {
            this.tag = tag; // 태그 추가 또는 수정
        }
    }

    public void delete() {
        this.status = Status.DELETED;
    }

}
