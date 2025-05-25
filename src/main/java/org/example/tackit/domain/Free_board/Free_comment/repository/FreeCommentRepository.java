package org.example.tackit.domain.Free_board.Free_comment.repository;

import org.example.tackit.domain.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreeCommentRepository extends JpaRepository<FreeComment, Long> {
    // 특정 게시글에 달린 모든 댓글 조회
    List<FreeComment> findByFreePost(FreePost post);
    // 댓글을 작성한 게시글 조회
    @Query("SELECT DISTINCT fc.freePost FROM FreeComment fc WHERE fc.writer = :writer")
    List<FreePost> findDistinctPostsByWriter(@Param("writer")Member writer);

    List<FreeComment> findByWriter(Member member);
}
