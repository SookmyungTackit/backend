package org.example.tackit.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memberId;
    private Role role;
    private String organization;

    private String action;              // view_Post, search, ..
    private String resource;            // 게시글 ID, URL

    @Column(name = "request_uri")
    private String requestUri;
    private String memberAgent;
    private String ipAddress;

    private LocalDateTime timestamp;
    private long executionTime;


}
