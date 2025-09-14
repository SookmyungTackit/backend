package org.example.tackit.domain.Tip_board.Tip_tag.repository;

import org.example.tackit.domain.Tip_board.Tip_tag.dto.response.TipTagPostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TipTagCustomRepository {
    Page<TipTagPostResponseDto> findPostsByTagId(Long tagId, String organization, Pageable pageable);
}
