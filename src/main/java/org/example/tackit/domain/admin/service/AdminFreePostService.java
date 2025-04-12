package org.example.tackit.domain.admin.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.admin.dto.FreePostDTO;
import org.example.tackit.domain.admin.repository.AdminFreePostRepository;
import org.example.tackit.domain.entity.FreePost;
import org.example.tackit.domain.entity.Status;
import org.example.tackit.domain.free_post.repository.FreePostJPARepository;
import org.example.tackit.domain.free_post.service.FreePostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminFreePostService {
    private final AdminFreePostRepository adminFreePostRepository;
    private final FreePostJPARepository freePostJPARepository;

    // 비활성화 자유 게시글 전체 조회
    public List<FreePostDTO> getDeletedPosts() {
        List<FreePost> deletedPosts = adminFreePostRepository.findAllByStatus(Status.DELETED);

        return deletedPosts.stream()
                .map(post -> new FreePostDTO(
                        post.getTitle(),
                        post.getWriter().getNickname(),
                        post.getCreatedAt(),
                        post.getReportCount()
                ))
                .collect(Collectors.toList());
    }

    // 신고 게시글 완전 삭제
    @Transactional
    public void deletePost(Long id) {
        if (!freePostJPARepository.existsById(id)) {
            throw new EntityNotFoundException("해당 게시글이 존재하지 않습니다.");
        }

        freePostJPARepository.deleteById(id);
    }

    // 신고 게시글 활성 전환
    @Transactional
    public void activatePost(Long id) {
        FreePost post = freePostJPARepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("해당 게시글이 존재하지 않습니다."));

        post.activate();
    }


}
