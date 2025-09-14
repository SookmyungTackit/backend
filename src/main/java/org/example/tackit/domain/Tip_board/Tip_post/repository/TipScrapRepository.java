package org.example.tackit.domain.Tip_board.Tip_post.repository;

import org.example.tackit.domain.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TipScrapRepository extends JpaRepository<TipScrap, Long> {
    Page<TipScrap> findByMemberAndType(Member member, Post type, Pageable pageable);
    Optional<TipScrap> findByMemberAndTipPost(Member member, TipPost tipPost);
}
