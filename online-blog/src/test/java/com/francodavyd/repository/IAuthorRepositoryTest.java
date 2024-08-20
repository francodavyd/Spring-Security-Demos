package com.francodavyd.repository;

import com.francodavyd.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
@DataJpaTest
public class IAuthorRepositoryTest {
    @Autowired
    private IAuthorRepository repository;
    private Author author;

    @BeforeEach
    public void setup(){
        author = Author.builder()
                .name("Franco")
                .lastName("Sanchez")
                .postsList(Collections.emptySet())
                .build();
        System.out.println("Hello");
    }
    @DisplayName("Test para guardar un autor")
    @Test
    public void saveTest(){
        Author saveAuthor = repository.save(author);
        assertThat(saveAuthor).isNotNull();
        assertThat(saveAuthor.getId()).isNotNull();
        assertThat(saveAuthor.getName()).isEqualTo("Franco");
    }
    @DisplayName("Test para obtener lista de autores")
    @Test
    void getAllTest(){
        repository.save(author);
        List<Author> listaAutores = repository.findAll();
        assertThat(listaAutores).isNotNull();

        assertThat(listaAutores.size()).isEqualTo(1);
    }
    @DisplayName("Test para obtener un autor por ID")
    @Test
    void findByIdTest(){
        repository.save(author);
        Author authorBD = repository.findById(author.getId()).get();
        assertThat(authorBD).isNotNull();
        assertThat(authorBD.getName()).isEqualTo("Franco");
    }
    @DisplayName("Test para eliminar un autor")
    @Test
    public void deleteByIdTest(){
        repository.save(author);
        repository.deleteById(author.getId());
        Optional<Author> findAuthor = repository.findById(author.getId());
        assertThat(findAuthor).isEmpty();
    }
    @DisplayName("Test para actualizar un autor")
    @Test
    public void updateTest(){
        repository.save(author);

        Author autorGuardado = repository.findById(author.getId()).get();
        autorGuardado.setName("Francisco");
        autorGuardado.setLastName("Piedemont");
        Author autorActualizado = repository.save(autorGuardado);

        assertThat(autorActualizado).isNotNull();
        assertThat(autorActualizado.getName()).isEqualTo("Francisco");
        assertThat(autorActualizado.getLastName()).isEqualTo("Piedemont");
    }

}
