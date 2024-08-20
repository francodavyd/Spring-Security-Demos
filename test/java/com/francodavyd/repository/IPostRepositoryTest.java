package com.francodavyd.repository;

import com.francodavyd.model.Author;
import com.francodavyd.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
public class IPostRepositoryTest {
    @Autowired
    private IPostRepository repository;
    private Post post;

    @BeforeEach
    public void setup(){
        post = Post.builder()
                .title("The Moon")
                .description("Medieval history book")
                .creationDate(LocalDate.now())
                .authorsList(new HashSet<>(Arrays.asList(
                        Author.builder()
                                .name("Tito")
                                .lastName("Handiamo")
                                .postsList(Collections.emptySet())
                                .build()
                )))
                .build();
        System.out.println("Hello");
    }
    @DisplayName("Test para guardar un post")
    @Test
    public void saveTest(){
        Post savePost = repository.save(post);
        assertThat(savePost).isNotNull();
        assertThat(savePost.getId()).isNotNull();
        assertThat(savePost.getTitle()).isEqualTo("The Moon");
    }
    @DisplayName("Test para obtener lista de posts")
    @Test
    void getAllTest(){
        repository.save(post);
        List<Post> listaPosts = repository.findAll();
        assertThat(listaPosts).isNotNull();

        assertThat(listaPosts.size()).isEqualTo(1);
    }
    @DisplayName("Test para obtener un post por ID")
    @Test
    void findByIdTest(){
        repository.save(post);
        Post postBD = repository.findById(post.getId()).get();
        assertThat(postBD).isNotNull();
        assertThat(postBD.getTitle()).isEqualTo("The Moon");
    }
    @DisplayName("Test para eliminar un post")
    @Test
    public void deleteByIdTest(){
        repository.save(post);
        repository.deleteById(post.getId());
        Optional<Post> findPost = repository.findById(post.getId());
        assertThat(findPost).isEmpty();
    }
    @DisplayName("Test para actualizar un post")
    @Test
    public void updateTest(){
        repository.save(post);

        Post postGuardado = repository.findById(post.getId()).get();
        postGuardado.setTitle("The Black Moon");
        Post postActualizado = repository.save(postGuardado);

        assertThat(postActualizado).isNotNull();
        assertThat(postActualizado.getTitle()).isEqualTo("The Black Moon");

    }
}
