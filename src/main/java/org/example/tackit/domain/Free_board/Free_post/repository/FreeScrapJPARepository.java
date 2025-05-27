package org.example.tackit.domain.Free_board.Free_post.repository;

import org.example.tackit.domain.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FreeScrapJPARepository extends JpaRepository<FreeScrap, Long> {
    boolean existsByMemberAndFreePost(Member member, FreePost freePost);
    Page<FreeScrap> findByMemberAndType(Member member, Post type, Pageable pageable);
}
