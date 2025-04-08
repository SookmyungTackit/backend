package org.example.tackit.domain.admin.repository;

import org.example.tackit.domain.entity.FreePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminFreePostRepository extends JpaRepository<FreePost, Long> {
    // 신고된 게시글 목록
    List<FreePost> findByReportCountGreaterThanEqual(int count);
}
