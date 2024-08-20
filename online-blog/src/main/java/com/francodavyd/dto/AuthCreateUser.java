<<<<<<< HEAD
package com.francodavyd.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
public record AuthCreateUser(@NotBlank String username, @NotBlank String password,@Valid AuthCreateRoleRequest authCreateRoleRequest) {
}
=======
package com.francodavyd.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public record AuthCreateUser(@NotBlank String username, @NotBlank String password,@Valid AuthCreateRoleRequest authCreateRoleRequest) {
}
>>>>>>> 1da008a75635c65c3167e712ecb49d3a2672cf0c
