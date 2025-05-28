package org.example.tackit.domain.admin.repository;

import org.example.tackit.domain.entity.FreePost;
import org.example.tackit.domain.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminFreePostRepository extends JpaRepository<FreePost, Long> {
    // 비활성화된 게시글 목록
    Page<FreePost> findAllByStatus(Status status, Pageable pageable);


}
