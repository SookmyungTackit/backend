package org.example.tackit.domain.notification.controller;


import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.auth.login.security.CustomUserDetails;
import org.example.tackit.domain.notification.dto.resp.NotificationRespDto;
import org.example.tackit.domain.notification.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/api/notify")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    // [ 실시간 알림을 위한 SSE 구독 ]
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@AuthenticationPrincipal CustomUserDetails user) {
        Long userId = user.getId();
        return notificationService.subscribe(userId);
    }

    // [ 모든 읽지 않은 알림 조회 ]
    @GetMapping
    public ResponseEntity<List<NotificationRespDto>> getAllNotifications(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user.getId();

        List<NotificationRespDto> allNotifications = notificationService.findAllNotifications(userId);

        return ResponseEntity.ok(allNotifications);
    }

}
