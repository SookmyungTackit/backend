package org.example.tackit.domain.QnA_board.QnA_post.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.QnA_board.QnA_tag.repository.QnAPostTagMapRepository;
import org.example.tackit.domain.QnA_board.QnA_tag.repository.QnATagRepository;
import org.example.tackit.domain.entity.QnAPost;
import org.example.tackit.domain.entity.QnATag;
import org.example.tackit.domain.entity.QnATagMap;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnAPostTagService {
    private final QnATagRepository qnATagRepository;
    private final QnAPostTagMapRepository qnAPostTagMapRepository;

    // 태그 매핑 저장
    public List<String> assignTagsToPost(QnAPost post, List<Long> tagIds) {
        List<String> tagNames = new ArrayList<>();

        for (Long tagId : tagIds) {
            QnATag tag = qnATagRepository.findById(tagId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 태그입니다."));
            qnAPostTagMapRepository.save(QnATagMap.builder()
                    .qnaPost(post)
                    .tag(tag)
                    .build());
            tagNames.add(tag.getTagName());
        }

        return tagNames;
    }

    // 게시글에 연결된 태그 모두 삭제
    public void deleteTagsByPost(QnAPost post) {
        qnAPostTagMapRepository.deleteAllByQnaPost(post);
    }

    // 게시글에 연결된 태그 이름 조회
    public List<String> getTagNamesByPost(QnAPost post) {
        return qnAPostTagMapRepository.findByQnaPost(post).stream()
                .map(mapping -> mapping.getTag().getTagName())
                .toList();
    }


}
