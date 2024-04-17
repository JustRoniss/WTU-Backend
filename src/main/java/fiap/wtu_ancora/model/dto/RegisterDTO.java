package fiap.wtu_ancora.model.dto;

import fiap.wtu_ancora.model.UserRole;

public record RegisterDTO(String name, String email, String password, UserRole role) {
}
