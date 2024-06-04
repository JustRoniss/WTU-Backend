package fiap.wtu_ancora.dto;

import fiap.wtu_ancora.model.UserRole;

public record RegisterDTO(String name, String email, String password, UserRole role, Long unitId) {
}