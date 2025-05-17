package org.example.tackit.domain.Tip_board.repository;

import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.TipPost;
import org.example.tackit.domain.entity.TipScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipScrapRepository extends JpaRepository<TipScrap, Long> {
    boolean existsByMemberAndTipPost(Member member, TipPost tipPost);
    List<TipScrap> findByMember(Member member);
}
