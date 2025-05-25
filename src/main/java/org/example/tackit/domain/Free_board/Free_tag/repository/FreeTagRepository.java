package org.example.tackit.domain.Free_board.Free_tag.repository;

import org.example.tackit.domain.entity.FreeTag;
import org.example.tackit.domain.entity.QnATag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreeTagRepository extends JpaRepository<FreeTag, Long> {
    Optional<QnATag> findByTagName(String tagName);
}
