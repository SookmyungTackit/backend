package org.example.tackit.domain.QnA_board.QnA_tag.repository;

import org.example.tackit.domain.entity.QnAPost;
import org.example.tackit.domain.entity.QnATag;
import org.example.tackit.domain.entity.QnATagMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QnATagRepository extends JpaRepository<QnATag, Long> {
    // 태그 이름으로 중복 검사 등
    Optional<QnATag> findByTagName(String tagName);
}
