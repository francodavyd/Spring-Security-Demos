package com.francodavyd.repository;

import com.francodavyd.model.Permission;
import com.francodavyd.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
public class IRoleRepositoryTest {
    @Autowired
    private IRoleRepository repository;
    private Role role;

    @BeforeEach
    public void setup(){
        role = Role.builder()
                .role("ADMIN")
                .permissionsList(new HashSet<>(Arrays.asList(
                        Permission.builder().permissionName("CREATE").build(),
                        Permission.builder().permissionName("READ").build(),
                        Permission.builder().permissionName("UPDATE").build(),
                        Permission.builder().permissionName("DELETE").build()
                )))
                .build();
        System.out.println("Hello");
    }
    @DisplayName("Test para guardar un role")
    @Test
    public void saveTest(){
        Role saveRole = repository.save(role);
        assertThat(saveRole).isNotNull();
        assertThat(saveRole.getId()).isNotNull();
        assertThat(saveRole.getRole()).isEqualTo("ADMIN");
    }
    @DisplayName("Test para obtener lista de roles")
    @Test
    void getAllTest(){
        repository.save(role);
        List<Role> listaRoles = repository.findAll();
        assertThat(listaRoles).isNotNull();

        assertThat(listaRoles.size()).isEqualTo(1);
    }
    @DisplayName("Test para obtener un rol por ID")
    @Test
    void findByIdTest(){
        repository.save(role);
        Role roleBD = repository.findById(role.getId()).get();
        assertThat(roleBD).isNotNull();
        assertThat(roleBD.getRole()).isEqualTo("ADMIN");
    }
    @DisplayName("Test para eliminar un rol")
    @Test
    public void deleteByIdTest(){
        repository.save(role);
        repository.deleteById(role.getId());
        Optional<Role> findRole = repository.findById(role.getId());
        assertThat(findRole).isEmpty();
    }
    @DisplayName("Test para actualizar un rol")
    @Test
    public void updateTest(){
        repository.save(role);

        Role roleGuardado = repository.findById(role.getId()).get();
        roleGuardado.setRole("MANAGER");
        Role roleActualizado = repository.save(roleGuardado);

        assertThat(roleActualizado).isNotNull();
        assertThat(roleActualizado.getRole()).isEqualTo("MANAGER");
        assertThat(roleActualizado.getPermissionsList().size()).isEqualTo(4);
     }
}
