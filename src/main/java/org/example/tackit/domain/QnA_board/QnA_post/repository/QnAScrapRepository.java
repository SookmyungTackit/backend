package org.example.tackit.domain.QnA_board.QnA_post.repository;

import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.Post;
import org.example.tackit.domain.entity.QnAPost;
import org.example.tackit.domain.entity.QnAScrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QnAScrapRepository extends JpaRepository<QnAScrap, Long> {
    Optional<QnAScrap> findByUserAndQnaPost(Member user, QnAPost qnaPost);
    void deleteByUserAndQnaPost(Member user, QnAPost qnaPost);
    List<QnAScrap> findByUserAndType(Member user, Post type);
}
