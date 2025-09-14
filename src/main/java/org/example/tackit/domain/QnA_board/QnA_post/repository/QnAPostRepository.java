package org.example.tackit.domain.QnA_board.QnA_post.repository;

import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.Post;
import org.example.tackit.domain.entity.QnAPost;
import org.example.tackit.domain.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnAPostRepository extends JpaRepository<QnAPost, Long> {
    // 선택적으로 질문 게시글만 조회할 때 사용
    List<QnAPost> findByType(Post type);
    // 상태로 조회
    Page<QnAPost> findAllByStatusAndWriter_Organization(Status status, String org, Pageable pageable);
    Page<QnAPost> findByWriterAndStatus(Member writer, Status status, Pageable pageable);
}
