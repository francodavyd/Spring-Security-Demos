package com.francodavyd.service.implementations;

import com.francodavyd.model.Permission;
import com.francodavyd.repository.IPermissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PermissionServiceTest {
    @Mock
    private IPermissionRepository repository;
    @InjectMocks
    private PermissionServiceImpl service;
    private Permission permission;
    @BeforeEach
    public void setup(){
        permission = Permission.builder()
                .permissionName("CREATE")
                .build();
    }

    @DisplayName("Test para guardar un permiso")
    @Test
    public void saveTest(){
        given(repository.save(permission)).willReturn(permission);
        Permission permisoGuardado = service.save(permission);
        assertThat(permisoGuardado).isNotNull();
        assertThat(permisoGuardado.getPermissionName()).isEqualTo("CREATE");
    }
    @DisplayName("Test para obtener lista de permisos")
    @Test
    public void getAllTest(){
        given(repository.findAll()).willReturn(List.of(permission));

        List<Permission> permisos = service.findAll();
        assertThat(permisos).isNotNull();
        assertThat(permisos.size()).isEqualTo(1);
    }

    @DisplayName("Test para obtener un permiso por ID")
    @Test
    public void findByIdTest(){
        given(repository.findById(permission.getId())).willReturn(Optional.of(permission));

        Permission permisoGuardado = service.findById(permission.getId()).get();

        assertThat(permisoGuardado).isNotNull();
    }

    @DisplayName("Test para actualizar un permiso")
    @Test
    public void updateTest(){
        given(repository.save(permission)).willReturn(permission);
        given(repository.findById(permission.getId())).willReturn(Optional.of(permission));
        permission.setPermissionName("UPDATE");
        Permission permisoActualizado  = service.update(permission.getId(), permission);
        assertThat(permisoActualizado).isNotNull();
        assertThat(permisoActualizado.getPermissionName()).isEqualTo("UPDATE");
    }

    @DisplayName("Test para eliminar un permiso")
    @Test
    public void deleteByIdTest(){

        willDoNothing().given(repository).deleteById(permission.getId());

        service.deleteById(permission.getId());

        verify(repository,times(1)).deleteById(permission.getId());

    }



}
