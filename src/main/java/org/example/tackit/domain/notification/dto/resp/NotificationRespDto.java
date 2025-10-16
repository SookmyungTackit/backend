package org.example.tackit.domain.notification.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.tackit.domain.entity.Notification;
import org.example.tackit.domain.entity.NotificationType;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NotificationRespDto {
    private Long id;
    private String from;
    private String message;
    private String relatedUrl;
    private boolean isRead;
    private NotificationType type;
    private LocalDateTime createdAt;


    public NotificationRespDto(Notification notification, String fromNickname) {
        this.id = notification.getId();
        this.from = fromNickname;
        this.message = notification.getMessage();
        this.relatedUrl = notification.getRelatedUrl();
        this.isRead = notification.isRead();
        this.type = notification.getType();
        this.createdAt = notification.getCreatedAt();
    }
}
