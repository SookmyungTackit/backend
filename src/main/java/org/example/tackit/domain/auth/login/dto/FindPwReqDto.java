package org.example.tackit.domain.auth.login.dto;

import lombok.Data;

@Data
public class FindPwReqDto {
    private String organization;
    private String name;
    private String email;
}
