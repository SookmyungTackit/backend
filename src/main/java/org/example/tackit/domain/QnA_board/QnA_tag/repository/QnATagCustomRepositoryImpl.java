package org.example.tackit.domain.QnA_board.QnA_tag.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.example.tackit.domain.QnA_board.QnA_tag.dto.response.QQnATagPostResponseDto;
import org.example.tackit.domain.QnA_board.QnA_tag.dto.response.QnATagPostResponseDto;
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

@Repository
public class QnATagCustomRepositoryImpl implements QnATagCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    // 스프링이 JPAQueryFactory를 주입
    public QnATagCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<QnATagPostResponseDto> findPostsByTagId(Long tagId, Pageable pageable){
        List<QnATagPostResponseDto> content = jpaQueryFactory
                .select(new QQnATagPostResponseDto(
                        qnAPost.id,
                        member.nickname,
                        qnAPost.title,
                        qnAPost.content,
                        qnAPost.createdAt
                ))
                .from(qnAPost)
                .join(qnAPost.writer, member)
                .join(qnAPost.tagMaps, qnATagMap)
                .join(qnATagMap.tag, qnATag)
                .where(
                        qnATag.id.eq(tagId),
                        qnAPost.status.eq(Status.ACTIVE)
                )
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qnAPost.createdAt.desc())
                .fetch();

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
