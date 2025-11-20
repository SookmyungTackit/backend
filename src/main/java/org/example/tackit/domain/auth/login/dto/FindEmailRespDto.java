package org.example.tackit.domain.auth.login.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindEmailRespDto {
    private String email;
}
