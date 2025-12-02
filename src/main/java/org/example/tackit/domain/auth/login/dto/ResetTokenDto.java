package org.example.tackit.domain.auth.login.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResetTokenDto {
    private String grantType;
    private String resetToken;
    private Long expiresIn;
}
