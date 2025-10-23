package org.example.tackit.domain.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.tackit.domain.entity.Role;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberMypageResponse {
    private String nickname;
    private String email;
    private String organization;
    private Role role;
    private int joinedYear;
    private int yearsOfService;
    private String profileImageUrl;
}
