package org.example.tackit.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class TipPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member writer;

    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Status status = Status.ACTIVE;

    @Builder
    public TipPost(Member writer, String title, String content, LocalDateTime createdAt, Status status) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.status = (status != null) ? status : Status.ACTIVE;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void delete() {
        this.status = Status.DELETED;
    }
}
