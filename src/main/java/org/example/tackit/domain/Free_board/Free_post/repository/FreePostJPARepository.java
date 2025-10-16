package org.example.tackit.domain.Free_board.Free_post.repository;

import org.example.tackit.domain.entity.FreePost;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FreePostJPARepository extends JpaRepository<FreePost, Long> {
    Page<FreePost> findByWriterAndStatus(Member member, Status status, Pageable pageable);

    Page<FreePost> findByOrganizationAndStatus(String organization, Status status, Pageable pageable);

    long countByStatus(Status status);
}
