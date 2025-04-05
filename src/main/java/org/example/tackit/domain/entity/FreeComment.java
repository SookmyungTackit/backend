package org.example.tackit.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class FreeComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User writer;

    @ManyToOne
    @JoinColumn(name = "free_id", nullable = false)
    private FreePost freeId;

    private String content;
    private LocalDateTime createdAt;
}
