package com.francodavyd.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AuthorDTO {
    @NotNull
    @NotBlank
    private Long id;
}
