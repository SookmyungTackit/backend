package org.example.tackit.domain.Tip_board.repository;


import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.Status;
import org.example.tackit.domain.entity.TipPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipPostJPARepository extends JpaRepository<TipPost, Long> {
    List<TipPost> findByWriter(Member member);

    Page<TipPost> findByOrganizationAndStatus(String organization, Status status, Pageable pageable);

    Page<TipPost> findByWriterAndStatus(Member writer, Status status, Pageable pageable );

}
