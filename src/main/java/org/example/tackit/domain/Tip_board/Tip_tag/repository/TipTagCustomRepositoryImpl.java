package org.example.tackit.domain.Tip_board.Tip_tag.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.example.tackit.domain.Tip_board.Tip_tag.dto.response.TipTagPostResponseDto;
import org.example.tackit.domain.entity.Status;
import org.example.tackit.domain.entity.TipPost;
import org.example.tackit.domain.entity.TipTagMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.example.tackit.domain.entity.QMember.member;
import static org.example.tackit.domain.entity.QTipPost.tipPost;
import static org.example.tackit.domain.entity.QTipTagMap.tipTagMap;
import static org.example.tackit.domain.entity.QTipTag.tipTag;


// @Repository
public class TipTagCustomRepositoryImpl implements TipTagCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    // 스프링이 JPAQueryFactory를 주입
    public TipTagCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<TipTagPostResponseDto> findPostsByTagId(Long tagId, String organization, Pageable pageable) {
        // 1. 태그 ID로 해당 게시글 ID 조회

        List<Long> postIds = jpaQueryFactory
                .select(tipTagMap.tipPost.id)
                .from(tipTagMap)
                .where(tipTagMap.tag.id.eq(tagId))
                .fetch();

        if (postIds.isEmpty()) {
            return new PageImpl<>(List.of(), pageable, 0);
        }

        // 2. 게시글 + 작성자 정보 조회 (페이징 적용)
        List<TipPost> posts = jpaQueryFactory
                .selectFrom(tipPost)
                .join(tipPost.writer, member).fetchJoin()
                .where(tipPost.id.in(postIds),
                        tipPost.status.eq(Status.ACTIVE),
                        tipPost.writer.organization.eq(organization)
                )
                .orderBy(tipPost.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Long> pagedPostIds = posts.stream().map(TipPost::getId).toList();


        // 3. 태그 일괄 조회
        List<TipTagMap> tagMappings = jpaQueryFactory
                .selectFrom(tipTagMap)
                .join(tipTagMap.tag, tipTag).fetchJoin()
                .where(tipTagMap.tipPost.id.in(pagedPostIds))
                .fetch();

        Map<Long, List<String>> tagMap = tagMappings.stream()
                .collect(Collectors.groupingBy(
                        map -> map.getTipPost().getId(),
                        Collectors.mapping(m -> m.getTag().getTagName(), Collectors.toList())
                ));

        // 4. DTO 변환
        List<TipTagPostResponseDto> content = posts.stream()
                .map(post -> new TipTagPostResponseDto(
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
                .select(tipPost.countDistinct())
                .from(tipPost)
                .join(tipPost.tagMaps, tipTagMap)
                .join(tipTagMap.tag, tipTag)
                .where(
                        tipTag.id.eq(tagId),
                        tipPost.status.eq(Status.ACTIVE)
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total);

    }
}


