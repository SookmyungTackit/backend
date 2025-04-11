package org.example.tackit.domain.admin.repository;

import org.example.tackit.domain.entity.FreePost;
import org.example.tackit.domain.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminFreePostRepository extends JpaRepository<FreePost, Long> {
    // 비활성화된 게시글 목록
    List<FreePost> findAllByStatus(Status status);


}
