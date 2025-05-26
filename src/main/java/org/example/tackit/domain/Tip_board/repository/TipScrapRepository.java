package org.example.tackit.domain.Tip_board.repository;

import org.example.tackit.domain.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TipScrapRepository extends JpaRepository<TipScrap, Long> {
    boolean existsByUserAndTipPost(Member user, TipPost tipPost);
    List<TipScrap> findByUser(Member user);
    Optional<TipScrap> findByUserAndTipPost(Member user, TipPost tipPost);
    Page<TipScrap> findByUserAndType(Member user, Post type, Pageable pageable);
}
