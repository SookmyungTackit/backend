package org.example.tackit.domain.admin.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.admin.dto.ReportedPostDTO;
import org.example.tackit.domain.admin.repository.AdminTipPostRepository;
import org.example.tackit.domain.entity.Status;
import org.example.tackit.domain.entity.TipPost;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminTipPostService implements ReportedPostService{
    private final AdminTipPostRepository adminTipPostRepository;

    // 비활성화 게시글 전체 조회
    @Override
    public List<ReportedPostDTO> getDeletedPosts() {
        return adminTipPostRepository.findAllByStatus(Status.DELETED)
                .stream()
                .map(ReportedPostDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 신고 게시글 완전 삭제
    @Override
    @Transactional
    public void deletePost(Long id) {
        TipPost post = adminTipPostRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 존재하지 않습니다."));

        adminTipPostRepository.delete(post);
    }

    // 신고 게시글 활성 전환
    @Override
    @Transactional
    public void activatePost(Long id) {
        TipPost post = adminTipPostRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("해당 게시글이 존재하지 않습니다."));

        post.activate();
    }
}
