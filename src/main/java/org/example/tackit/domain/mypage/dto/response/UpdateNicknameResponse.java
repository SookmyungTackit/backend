package org.example.tackit.domain.mypage.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNicknameResponse {
    private String beforeNickname;
    private String afterNickname;
}
