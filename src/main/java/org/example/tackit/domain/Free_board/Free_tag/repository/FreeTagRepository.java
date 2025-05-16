package org.example.tackit.domain.Free_board.Free_tag.repository;

import org.example.tackit.domain.entity.FreeTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeTagRepository extends JpaRepository<FreeTag, Long> {
}
