package org.example.tackit.domain.auth.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.tackit.domain.entity.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
    private String email;
    private String password;
    private String nickname;
    private String organization;
    private Role role;
}

