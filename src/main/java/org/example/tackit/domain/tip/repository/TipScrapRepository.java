package org.example.tackit.domain.tip.repository;

import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.TipPost;
import org.example.tackit.domain.entity.TipScrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipScrapRepository extends JpaRepository<TipScrap, Long> {
    boolean existsByMemberAndTipPost(Member member, TipPost tipPost);
}
