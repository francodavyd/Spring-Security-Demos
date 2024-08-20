package com.francodavyd.repository;

import com.francodavyd.model.Permission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
public class IPermissionRepositoryTest {

    @Autowired
    private IPermissionRepository repository;
    private Permission permission;

    @BeforeEach
    public void setup(){
        permission = Permission.builder()
                        .permissionName("CREATE")
                        .build();
        System.out.println("Hello");
    }
    @DisplayName("Test para guardar un permiso")
    @Test
    public void saveTest(){
        Permission savePermission = repository.save(permission);
        assertThat(savePermission).isNotNull();
        assertThat(savePermission.getId()).isNotNull();
        assertThat(savePermission.getPermissionName()).isEqualTo("CREATE");
    }
    @DisplayName("Test para obtener lista de permisos")
    @Test
    void getAllTest(){
        Permission permission2 = Permission.builder().permissionName("READ").build();
        repository.save(permission);
        repository.save(permission2);
        List<Permission> listaPermisos = repository.findAll();
        assertThat(listaPermisos).isNotNull();

        assertThat(listaPermisos.size()).isEqualTo(2);
    }
    @DisplayName("Test para obtener un permiso por ID")
    @Test
    void findByIdTest(){
        repository.save(permission);
        Permission permissionBD = repository.findById(permission.getId()).get();
        assertThat(permissionBD).isNotNull();
        assertThat(permissionBD.getPermissionName()).isEqualTo("CREATE");
    }
    @DisplayName("Test para eliminar un permiso")
    @Test
    public void deleteByIdTest(){
        repository.save(permission);
        repository.deleteById(permission.getId());
        Optional<Permission> findPermission = repository.findById(permission.getId());
        assertThat(findPermission).isEmpty();
    }
    @DisplayName("Test para actualizar un permiso")
    @Test
    public void updateTest(){
        repository.save(permission);

        Permission permisoGuardado = repository.findById(permission.getId()).get();
        permisoGuardado.setPermissionName("UPDATE");
        Permission permisoActualizado = repository.save(permisoGuardado);

        assertThat(permisoActualizado).isNotNull();
        assertThat(permisoActualizado.getPermissionName()).isEqualTo("UPDATE");
    }
}
