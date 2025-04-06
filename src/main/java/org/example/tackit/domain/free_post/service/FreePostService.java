package org.example.tackit.domain.free_post.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.entity.FreePost;
import org.example.tackit.domain.entity.Status;
import org.example.tackit.domain.entity.User;
import org.example.tackit.domain.free_post.dto.request.FreePostCreateDTO;
import org.example.tackit.domain.free_post.dto.request.FreePostUpdateDTO;
import org.example.tackit.domain.free_post.dto.response.FreePostDTO;
import org.example.tackit.domain.free_post.repository.FreePostJPARepository;
import org.example.tackit.domain.free_post.repository.UserJPARepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FreePostService {
    private final FreePostJPARepository freePostJPARepository;
    private final UserJPARepository userJPARepository;

    // [ 게시글 전체 조회 ]
    public List<FreePostDTO> getAllActivePosts() {
        List<FreePost> posts = freePostJPARepository.findAllByStatus(Status.ACTIVE);
        return posts.stream()
                .map(FreePostDTO::new)
                .collect(Collectors.toList());
    }


    // [ 게시글 상세 조회 ]
    public FreePostDTO getPostById(Long id) {
        FreePost freePost = freePostJPARepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        return new FreePostDTO(freePost);
    }

    // [ 게시글 작성 ]
    @Transactional
    public FreePostDTO createPost(FreePostCreateDTO dto) {
        // 1. nickname으로 유저 조회
        User user = userJPARepository.findByNickname(dto.getNickname())
                .orElseThrow( () -> new IllegalArgumentException("작성자가 DB에 존재하지 않습니다."));

        // 2. 게시글 생성
        FreePost newPost = FreePost.builder()
                        .writer(user)
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .tag(dto.getTag())
                        .createdAt(LocalDateTime.now())
                        .build();

        freePostJPARepository.save(newPost);
        return new FreePostDTO(newPost);
    }

    // [ 게시글 수정 ]
    @Transactional
    public FreePostDTO updatePost(Long id, FreePostUpdateDTO dto) {
        FreePost post = freePostJPARepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        post.update(dto.getTitle(), dto.getContent(), dto.getTag());
        return new FreePostDTO(post);
    }

    // [ 게시글 삭제 ]
    @Transactional
    public void deletePost(Long id) {
        FreePost post = freePostJPARepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        post.delete();
    }

}
