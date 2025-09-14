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
@Table(name = "tip_tag_map")
public class TipTagMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tip_post_id")
    private TipPost tipPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tip_tag_id")
    private TipTag tag;
}
