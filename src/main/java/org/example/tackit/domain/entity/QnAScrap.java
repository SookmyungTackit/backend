package org.example.tackit.domain.entity;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member memberId;

    @ManyToOne
    @JoinColumn(name = "qna_id", nullable = false)
    private FreePost qnaId;

    private LocalDateTime saved_at;
}
