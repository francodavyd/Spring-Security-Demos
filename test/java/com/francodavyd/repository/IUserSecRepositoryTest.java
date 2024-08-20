package com.francodavyd.repository;

import com.francodavyd.model.Permission;
import com.francodavyd.model.Role;
import com.francodavyd.model.UserSec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
public class IUserSecRepositoryTest {
    @Autowired
    private IUserSecRepository repository;
    private UserSec userSec;

    @BeforeEach
    public void setup(){
        userSec = UserSec.builder()
                .username("GamaTest123")
                .password("examplepassword")
                .enabled(true)
                .accountNotLocked(true)
                .accountNotExpired(true)
                .credentialNotExpired(true)
                .rolesList(new HashSet<>(Arrays.asList(
                        Role.builder()
                                .role("USER")
                                .permissionsList(new HashSet<>(Arrays.asList(
                                        Permission.builder()
                                                .permissionName("READ")
                                                .build()
                                )))
                                .build()
                )))
                .build();
        System.out.println("Hello");
    }
    @DisplayName("Test para guardar un usuario")
    @Test
    public void saveTest(){
        UserSec saveUser = repository.save(userSec);
        assertThat(saveUser).isNotNull();
        assertThat(saveUser.getId()).isNotNull();
        assertThat(saveUser.getUsername()).isEqualTo("GamaTest123");
    }
    @DisplayName("Test para obtener lista de usuarios")
    @Test
    void getAllTest(){
        repository.save(userSec);
        List<UserSec> listaUsuarios = repository.findAll();
        assertThat(listaUsuarios).isNotNull();
        assertThat(listaUsuarios.size()).isEqualTo(1);
    }
    @DisplayName("Test para obtener un usuario por ID")
    @Test
    void findByIdTest(){
        repository.save(userSec);
        UserSec usuarioBD = repository.findById(userSec.getId()).get();
        assertThat(usuarioBD).isNotNull();
        assertThat(usuarioBD.getPassword()).isEqualTo("examplepassword");
    }
    @DisplayName("Test para eliminar un usuario")
    @Test
    public void deleteByIdTest(){
        repository.save(userSec);
        repository.deleteById(userSec.getId());
        Optional<UserSec> findUsuario = repository.findById(userSec.getId());
        assertThat(findUsuario).isEmpty();
    }
    @DisplayName("Test para actualizar un usuario")
    @Test
    public void updateTest(){
        repository.save(userSec);

        UserSec usuarioGuardado = repository.findById(userSec.getId()).get();
        usuarioGuardado.setUsername("UsernameExample");
        usuarioGuardado.setEnabled(false);
        UserSec usuarioActualizado = repository.save(usuarioGuardado);

        assertThat(usuarioActualizado).isNotNull();
        assertThat(usuarioActualizado.getUsername()).isEqualTo("UsernameExample");
        assertThat(usuarioActualizado.isEnabled()).isEqualTo(false);
    }
}
