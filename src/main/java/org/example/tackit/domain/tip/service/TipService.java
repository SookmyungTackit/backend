package org.example.tackit.domain.tip.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.Status;
import org.example.tackit.domain.entity.TipPost;
import org.example.tackit.domain.entity.TipScrap;
import org.example.tackit.domain.free_post.repository.MemberJPARepository;
import org.example.tackit.domain.tip.dto.request.TipPostCreateDTO;
import org.example.tackit.domain.tip.dto.request.TipPostUpdateDTO;
import org.example.tackit.domain.tip.dto.response.TipPostDTO;
import org.example.tackit.domain.tip.repository.TipPostJPARepository;
import org.example.tackit.domain.tip.repository.TipScrapRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TipService {
    private final TipPostJPARepository tipPostJPARepository;
    private final MemberJPARepository memberJPARepository;
    private final TipScrapRepository tipScrapRepository;

    public List<TipPostDTO> getActivePostsByOrganization(String org) {
        List<TipPost> posts = tipPostJPARepository.findByOrganizationAndStatus(org, Status.ACTIVE);

        return posts.stream()
                .map(TipPostDTO::new)
                .collect(Collectors.toList());
    }
    // [ 게시글 전체 조회 ]
    public List<TipPostDTO> getAllActivePosts() {
        List<TipPost> posts = tipPostJPARepository.findAllByStatus(Status.ACTIVE);
        return posts.stream()
                .map(TipPostDTO::new)
                .collect(Collectors.toList());
    }

    // [ 게시글 상세 조회 ]
    public TipPostDTO getPostById(Long id) {
        TipPost tipPost = tipPostJPARepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        return new TipPostDTO(tipPost);
    }

    // [ 게시글 작성 ]
    @Transactional
    public TipPostDTO writePost(TipPostCreateDTO dto) {
        // 1. nickname으로 유저 조회
        Member member = memberJPARepository.findByNickname(dto.getNickname())
                .orElseThrow( () -> new IllegalArgumentException("작성자가 DB에 존재하지 않습니다."));

        // 2. 게시글 생성
        TipPost newPost = TipPost.builder()
                .writer(member)
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        tipPostJPARepository.save(newPost);
        return new TipPostDTO(newPost);
    }

    // [ 게시글 수정 ]
    @Transactional
    public TipPostDTO updatePost(Long id, TipPostUpdateDTO dto) {
        TipPost post = tipPostJPARepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다.") );

        post.update(dto.getTitle(), dto.getContent());
        return new TipPostDTO(post);
    }

    // [ 게시글 삭제 ]
    @Transactional
    public void deletePost(Long id) {
        TipPost post = tipPostJPARepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        post.delete();
    }

    // [ 게시글 스크랩 ]
    @Transactional
    public void scrapPost(Long id, Long memberId) {
        // 1. 게시글 조회
        TipPost post = tipPostJPARepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.") );

        // 2. 멤버 조회
        Member member = memberJPARepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        // 3. 중복 스크랩 방지
        boolean alreadyScrapped = tipScrapRepository.existsByMemberAndTipPost(member, post);
        if (alreadyScrapped) {
            throw new IllegalStateException("이미 스크랩한 게시글입니다.");
        }

        // 4. 스크랩 저장
        TipScrap scrap = new TipScrap(member, post);
        tipScrapRepository.save(scrap);
    }

}
