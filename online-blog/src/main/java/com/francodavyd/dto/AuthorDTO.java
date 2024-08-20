<<<<<<< HEAD
package com.francodavyd.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {
    @NotNull
    @NotBlank
    private Long id;


}
=======
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
>>>>>>> 1da008a75635c65c3167e712ecb49d3a2672cf0c
