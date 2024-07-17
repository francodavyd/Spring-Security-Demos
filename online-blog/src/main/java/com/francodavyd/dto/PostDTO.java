package com.francodavyd.dto;

import com.francodavyd.model.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
public class PostDTO {
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private Set<AuthorDTO> authorsList;
}
