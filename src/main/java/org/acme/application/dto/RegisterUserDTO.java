package org.acme.application.dto;

import lombok.Data;
import org.acme.domain.model.Role;

@Data
public class RegisterUserDTO {
    private String email;
    private String password;
    private Role role;
}
