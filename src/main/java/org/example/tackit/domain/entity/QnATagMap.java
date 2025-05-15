package org.example.tackit.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="qna_tag_map")
public class QnATagMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="qna_post_id")
    private QnAPost qnaPost;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "qna_tag_id")
    private QnATag tag;
}
