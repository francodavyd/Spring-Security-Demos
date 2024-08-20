package com.francodavyd.service.implementations;

import com.francodavyd.dto.AuthorDTO;
import com.francodavyd.dto.PostDTO;
import com.francodavyd.model.Author;
import com.francodavyd.model.Post;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import com.francodavyd.repository.IPostRepository;
import com.francodavyd.service.services.IAuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.*;
@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    private IPostRepository repository;
    @Mock
    private IAuthorService serviceAuthor;
    @InjectMocks
    private PostServiceImpl service;
    private Post post;
    private Author author;
    @BeforeEach
    public void setup(){
        author = Author.builder()
                .name("Pablo")
                .lastName("Asturia")
                .postsList(Collections.emptySet())
                .build();

        post = Post.builder()
                .title("Harry Potter")
                .description("Harry Potter game guides")
                .creationDate(LocalDate.now())
                .authorsList(new HashSet<>(Arrays.asList(
                        author
                )))
                .build();
    }
    @DisplayName("Test para guardar un post")
    @Test
    public void saveTest() {
        Set<AuthorDTO> authorDTOS = Set.of(new AuthorDTO(author.getId()));
        PostDTO postDTO = new PostDTO(post.getTitle(), post.getDescription(), authorDTOS);

        given(serviceAuthor.findById(author.getId())).willReturn(Optional.of(author));
        given(repository.save(any(Post.class))).willReturn(new Post());
        given(serviceAuthor.update(author.getId(), author)).willReturn(author);


        Post resultado = service.save(postDTO);


        assertThat(resultado).isNotNull();
        assertThat(resultado.getTitle()).isEqualTo(postDTO.getTitle());
        assertThat(resultado.getDescription()).isEqualTo(postDTO.getDescription());
        assertThat(resultado.getAuthorsList()).hasSize(1);
    }
    @DisplayName("Test para obtener lista de posts")
    @Test()
    public void getAllTest(){
        given(repository.findAll()).willReturn(List.of(post));

        List<Post> listaPost = service.findAll();

        assertThat(listaPost).isNotNull();
        assertThat(listaPost.size()).isEqualTo(1);
    }

    @DisplayName("Test para obtener un post por ID")
    @Test
    public void findByIdTest(){
        given(repository.findById(post.getId())).willReturn(Optional.of(post));

        Post postGuardado = service.findById(post.getId()).get();

        assertThat(postGuardado).isNotNull();
    }

    @DisplayName("Test para actualizar un post")
    @Test
    public void updateTest(){
        given(repository.save(post)).willReturn(post);
        given(repository.findById(post.getId())).willReturn(Optional.of(post));
        post.setTitle("Hello W");
        Post postGuardado  = service.update(post.getId(), post);
        assertThat(postGuardado).isNotNull();
        assertThat(post.getTitle()).isEqualTo("Hello W");
    }

    @DisplayName("Test para eliminar un post")
    @Test
    public void deleteByIdTest(){

        willDoNothing().given(repository).deleteById(post.getId());

        service.deleteById(post.getId());

        verify(repository,times(1)).deleteById(post.getId());

        Optional<Post> postOpt = repository.findById(post.getId());
        assertThat(postOpt).isEmpty();
    }

}
