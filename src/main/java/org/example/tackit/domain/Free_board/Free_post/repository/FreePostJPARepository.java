package org.example.tackit.domain.Free_board.Free_post.repository;

import org.example.tackit.domain.entity.FreePost;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreePostJPARepository extends JpaRepository<FreePost, Long> {
    List<FreePost> findByWriter(Member member);

    Page<FreePost> findByOrganizationAndStatus(String organization, Status status, Pageable pageable);
}
