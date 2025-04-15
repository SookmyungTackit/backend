package org.example.tackit.domain.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberMypageResponse {
    private String nickname;
    private int joinedYear;
    private int yearsOfService;
}
