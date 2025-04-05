package org.example.tackit.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class FreePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Post type;

    @Column(nullable = true)
    private String tag;
    private Status status;
    private int reportCount;
}
