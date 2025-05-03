package org.example.tackit.domain.tip.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.entity.Status;
import org.example.tackit.domain.entity.TipPost;
import org.example.tackit.domain.tip.dto.response.TipPostDTO;
import org.example.tackit.domain.tip.repository.TipPostJPARepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TipService {
    private final TipPostJPARepository tipPostJPARepository;

    // [ 게시글 전체 조회 ]
    public List<TipPostDTO> getAllActivePosts() {
        List<TipPost> posts = tipPostJPARepository.findAllByStatus(Status.ACTIVE);
        return posts.stream()
                .map(TipPostDTO::new)
                .collect(Collectors.toList());
    }
}
