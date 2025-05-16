package org.example.tackit.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member writer;

    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Post type;
    private String organization;

    @Column(nullable = true)
    private String tag;
    private Status status;
    private int reportCount = 0;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
    /*
    public void update(String title, String content, String tag) {
        this.title = title;
        this.content = content;

        if (tag == null || tag.trim().isEmpty()) {
            this.tag = null; // 태그 삭제
        } else {
            this.tag = tag; // 태그 추가 또는 수정
        }
    }
     */

    public void delete() {
        this.status = Status.DELETED;
    }

    public void increaseReportCount() {
        this.reportCount++;
        if (this.reportCount >= 3) {
            this.status = Status.DELETED;
        }
    }

    public void activate(){
        this.status = Status.ACTIVE;
        this.reportCount = 0;
    }
}
