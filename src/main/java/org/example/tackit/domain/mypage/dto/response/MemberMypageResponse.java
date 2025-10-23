package org.example.tackit.domain.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberMypageResponse {
    private String nickname;
    private int joinedYear;
    private int yearsOfService;
    private String profileImageUrl;
}
