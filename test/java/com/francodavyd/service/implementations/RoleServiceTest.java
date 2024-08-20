package com.francodavyd.service.implementations;

import com.francodavyd.model.Permission;
import com.francodavyd.model.Role;
import com.francodavyd.repository.IRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @Mock
    private IRoleRepository repository;
    @InjectMocks
    private RoleServiceImpl service;
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
    }
    @DisplayName("Test para guardar un rol")
    @Test
    public void saveTest(){
        given(repository.save(role)).willReturn(role);
        Role rolGuardado = service.save(role);
        assertThat(rolGuardado).isNotNull();
        assertThat(rolGuardado.getRole()).isEqualTo("ADMIN");
    }
    @DisplayName("Test para obtener lista de roles")
    @Test()
    public void getAllTest(){
        given(repository.findAll()).willReturn(List.of(role));

        List<Role> listaRoles = service.findAll();

        assertThat(listaRoles).isNotNull();
        assertThat(listaRoles.size()).isEqualTo(1);
    }

    @DisplayName("Test para obtener un rol por ID")
    @Test
    public void findByIdTest(){
        given(repository.findById(role.getId())).willReturn(Optional.of(role));

        Role rolGuardado = service.findById(role.getId()).get();

        assertThat(rolGuardado).isNotNull();
    }

    @DisplayName("Test para actualizar un rol")
    @Test
    public void updateTest(){
        given(repository.save(role)).willReturn(role);
        given(repository.findById(role.getId())).willReturn(Optional.of(role));
        role.setRole("MANAGER");
        Role rolGuardado  = service.update(role.getId(), role);
        assertThat(rolGuardado).isNotNull();
        assertThat(rolGuardado.getRole()).isEqualTo("MANAGER");
    }

    @DisplayName("Test para eliminar un role")
    @Test
    public void deleteByIdTest(){

        willDoNothing().given(repository).deleteById(role.getId());

        service.deleteById(role.getId());

        verify(repository,times(1)).deleteById(role.getId());

        Optional<Role> roleOpt = repository.findById(role.getId());
        assertThat(roleOpt).isEmpty();
    }


}
