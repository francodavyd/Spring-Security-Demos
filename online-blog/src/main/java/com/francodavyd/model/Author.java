package com.francodavyd.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
@Entity
@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "authors_posts", joinColumns = @JoinColumn(name = "author_id"),
    inverseJoinColumns = @JoinColumn(name = "post_id"))
    private Set<Post> postsList = new HashSet<>();
}
