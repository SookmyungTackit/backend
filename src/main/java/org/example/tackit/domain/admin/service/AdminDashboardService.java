package org.example.tackit.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.Free_board.Free_post.repository.FreePostJPARepository;
import org.example.tackit.domain.QnA_board.QnA_post.repository.QnAPostRepository;
import org.example.tackit.domain.Tip_board.Tip_post.repository.TipPostJPARepository;
import org.example.tackit.domain.admin.repository.UserLogRepository;
import org.example.tackit.domain.entity.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminDashboardService {
    private final TipPostJPARepository tipPostJPARepository;
    private final QnAPostRepository qnAPostRepository;
    private final FreePostJPARepository freePostJPARepository;

    private static final List<String> EXCLUDED_ACTIONS = Arrays.asList("sign-in", "sign-up", "sign-out");
    private final UserLogRepository userLogRepository;

    // [ 총 게시글 수 계산 ]
    public long getPostsCount() {
        long tipPostCount = tipPostJPARepository.count();
        long qnAPostCount = qnAPostRepository.count();
        long freePostCount = freePostJPARepository.count();

        return tipPostCount + qnAPostCount + freePostCount;
    }

    // [ DAU 계산 ]
    public Long getDau(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        return userLogRepository.findDauByTimestampBetween(startOfDay, endOfDay, EXCLUDED_ACTIONS);
    }

    // [ MAU 계산 ]
    public Long getMau(LocalDate date) {
        LocalDate firstDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());

        LocalDateTime startTime = firstDayOfMonth.atStartOfDay();
        LocalDateTime endTime = lastDayOfMonth.atTime(LocalTime.MAX);

        return userLogRepository.findMauByTimestampBetween(startTime, endTime, EXCLUDED_ACTIONS);
    }

    // [ 신고로 삭제된 게시글 수 계산 ]
    public Long getDeletedPostsByReport() {
        long deletedTips = tipPostJPARepository.countByStatus(Status.DELETED);
        long deletedFrees = freePostJPARepository.countByStatus(Status.DELETED);
        long deletedQnAs  = qnAPostRepository.countByStatus(Status.DELETED);

        return deletedTips + deletedFrees + deletedQnAs;
    }

    // [ 신고 게시글 조회 ]

}
