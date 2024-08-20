package com.francodavyd.service.implementations;

import com.francodavyd.model.Author;
import com.francodavyd.repository.IAuthorRepository;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    @Mock
    private IAuthorRepository repository;
    @InjectMocks
    private AuthorServiceImpl service;
    private Author author;
    @BeforeEach
    public void setup(){
        author = Author.builder()
                .name("Richard")
                .lastName("Madison")
                .postsList(Collections.emptySet())
                .build();
    }
    @DisplayName("Test para guardar un autor")
    @Test
    public void saveTest(){
        given(repository.save(author)).willReturn(author);
        Author autorGuardado = service.save(author);
        assertThat(autorGuardado).isNotNull();
        assertThat(autorGuardado.getLastName()).isEqualTo("Madison");
    }
    @DisplayName("Test para obtener lista de autores")
    @Test()
    public void getAllTest(){
    given(repository.findAll()).willReturn(List.of(author));

    List<Author> listaAutores = service.findAll();

    assertThat(listaAutores).isNotNull();
    assertThat(listaAutores.size()).isEqualTo(1);
   }

    @DisplayName("Test para obtener un autor por ID")
    @Test
    public void findByIdTest(){
        given(repository.findById(author.getId())).willReturn(Optional.of(author));

        Author autorGuardado = service.findById(author.getId()).get();

        assertThat(autorGuardado).isNotNull();
    }

    @DisplayName("Test para actualizar un autor")
    @Test
    public void updateTest(){
        given(repository.save(author)).willReturn(author);
        given(repository.findById(author.getId())).willReturn(Optional.of(author));
        author.setName("David");
        author.setLastName("Baranovich");
        Author autorGuardado  = service.update(author.getId(), author);
        assertThat(autorGuardado).isNotNull();
        assertThat(autorGuardado.getName()).isEqualTo("David");
        assertThat(autorGuardado.getLastName()).isEqualTo("Baranovich");
    }

    @DisplayName("Test para eliminar un autor")
    @Test
    public void deleteByIdTest(){

        willDoNothing().given(repository).deleteById(author.getId());

        service.deleteById(author.getId());

        verify(repository,times(1)).deleteById(author.getId());

        Optional<Author> authorOptional = repository.findById(author.getId());
        assertThat(authorOptional).isEmpty();
    }


}
