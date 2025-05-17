package org.example.tackit.domain.Free_board.Free_post.repository;

import org.example.tackit.domain.entity.FreePost;
import org.example.tackit.domain.entity.FreeScrap;
import org.example.tackit.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreeScrapJPARepository extends JpaRepository<FreeScrap, Long> {
    boolean existsByMemberAndFreePost(Member member, FreePost freePost);
    List<FreeScrap> findByMember(Member member);
}
