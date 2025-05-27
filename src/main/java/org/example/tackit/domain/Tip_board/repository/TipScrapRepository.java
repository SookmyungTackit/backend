package org.example.tackit.domain.Tip_board.repository;

import org.example.tackit.domain.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TipScrapRepository extends JpaRepository<TipScrap, Long> {
    boolean existsByMemberAndTipPost(Member member, TipPost tipPost);
    Page<TipScrap> findByMemberAndType(Member member, Post type, Pageable pageable);
}
