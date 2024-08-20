<<<<<<< HEAD
package com.francodavyd.dto;

import com.francodavyd.model.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
public class PostDTO {
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private Set<AuthorDTO> authorsList;


}
=======
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
>>>>>>> 1da008a75635c65c3167e712ecb49d3a2672cf0c
