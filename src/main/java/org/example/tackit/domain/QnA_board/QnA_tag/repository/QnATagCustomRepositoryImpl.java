package org.example.tackit.domain.QnA_board.QnA_tag.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.example.tackit.domain.QnA_board.QnA_tag.dto.response.QnATagPostResponseDto;
import org.example.tackit.domain.entity.QnAPost;
import org.example.tackit.domain.entity.QnATagMap;
import org.example.tackit.domain.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static org.example.tackit.domain.entity.QQnAPost.qnAPost;
import static org.example.tackit.domain.entity.QQnATagMap.qnATagMap;
import static org.example.tackit.domain.entity.QQnATag.qnATag;
import static org.example.tackit.domain.entity.QMember.member;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class QnATagCustomRepositoryImpl implements QnATagCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    // 스프링이 JPAQueryFactory를 주입
    public QnATagCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<QnATagPostResponseDto> findPostsByTagId(Long tagId, Pageable pageable){
        // 1. 태그 ID로 해당 게시글 ID 조회
        List<Long> postIds = jpaQueryFactory
                .select(qnATagMap.qnaPost.id)
                .from(qnATagMap)
                .where(qnATagMap.tag.id.eq(tagId))
                .fetch();

        if (postIds.isEmpty()) {
            return new PageImpl<>(List.of(), pageable, 0);
        }

        // 2. 게시글 + 작성자 정보 조회 (페이징 적용)
        List<QnAPost> posts = jpaQueryFactory
                .selectFrom(qnAPost)
                .join(qnAPost.writer, member).fetchJoin()
                .where(qnAPost.id.in(postIds), qnAPost.status.eq(Status.ACTIVE))
                .orderBy(qnAPost.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Long> pagedPostIds = posts.stream().map(QnAPost::getId).toList();


        // 3. 태그 일괄 조회
        List<QnATagMap> tagMappings = jpaQueryFactory
                .selectFrom(qnATagMap)
                .join(qnATagMap.tag, qnATag).fetchJoin()
                .where(qnATagMap.qnaPost.id.in(pagedPostIds))
                .fetch();

        Map<Long, List<String>> tagMap = tagMappings.stream()
                .collect(Collectors.groupingBy(
                        map -> map.getQnaPost().getId(),
                        Collectors.mapping(m -> m.getTag().getTagName(), Collectors.toList())
                ));

        // 4. DTO 변환
        List<QnATagPostResponseDto> content = posts.stream()
                .map(post -> new QnATagPostResponseDto(
                        post.getId(),
                        post.getWriter().getNickname(),
                        post.getTitle(),
                        post.getContent(),
                        post.getCreatedAt(),
                        tagMap.getOrDefault(post.getId(), List.of())
                ))
                .toList();

        // 전체 개수 조회 (총 몇 개 있는지)
        Long total = jpaQueryFactory
                .select(qnAPost.countDistinct())
                .from(qnAPost)
                .join(qnAPost.tagMaps, qnATagMap)
                .join(qnATagMap.tag, qnATag)
                .where(
                        qnATag.id.eq(tagId),
                        qnAPost.status.eq(Status.ACTIVE)
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }


}
