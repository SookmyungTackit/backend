package org.example.tackit.domain.notification.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.NotificationType;

@Getter
@RequiredArgsConstructor
public class NotificationEventDto {
    private final Member receiver;
    private final NotificationType type;
    private final String message;
    private final String relatedUrl;
}
