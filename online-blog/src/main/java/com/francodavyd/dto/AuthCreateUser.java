package com.francodavyd.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public record AuthCreateUser(@NotBlank String username, @NotBlank String password,@Valid AuthCreateRoleRequest authCreateRoleRequest) {
}
