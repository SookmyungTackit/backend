package org.example.tackit.domain.auth.login.dto;

import lombok.Data;

@Data
public class FindEmailReqDto {
    private String organization;
    private String name;
}
