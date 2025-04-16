package org.example.tackit.domain.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class UpdateNicknameRequest {
    private String nickname;
}
