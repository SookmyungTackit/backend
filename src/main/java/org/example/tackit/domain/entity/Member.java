package org.example.tackit.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.tackit.domain.mypage.dto.MemberMypageResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false, name = "joined_year")
    private int joinedYear;

    private Status status;
    private LocalDateTime createdAt = LocalDateTime.now();

    // 연차 계산 책임은 Member 도메인 내부에 분리
    // member 도메인 내에서만 쓰이기 때문에 private
    private int calculateYearsOfService() {
        return LocalDate.now().getYear() - this.joinedYear + 1;
    }

    // 마이페이지 응답을 만들어 반환한다.
    public MemberMypageResponse generateMypageResponse() {
        return new MemberMypageResponse(
                this.nickname,
                this.joinedYear,
                this.calculateYearsOfService()
        );
    }
}
