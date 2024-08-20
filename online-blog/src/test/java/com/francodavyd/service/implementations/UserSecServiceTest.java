package com.francodavyd.service.implementations;

import com.francodavyd.model.Author;
import com.francodavyd.model.Permission;
import com.francodavyd.model.Role;
import com.francodavyd.model.UserSec;
import com.francodavyd.repository.IUserSecRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserSecServiceTest {
    @Mock
    private IUserSecRepository repository;
    @InjectMocks
    private UserSecServiceImpl service;
    private UserSec user;
    @BeforeEach
    public void setup(){
        user = UserSec.builder()
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
    }
    @DisplayName("Test para guardar un user")
    @Test
    public void saveTest(){
        given(repository.save(user)).willReturn(user);
        UserSec userGuardado = service.save(user);
        assertThat(userGuardado).isNotNull();
        assertThat(userGuardado.getUsername()).isEqualTo("GamaTest123");
    }
    @DisplayName("Test para obtener lista de users")
    @Test()
    public void getAllTest(){
        given(repository.findAll()).willReturn(List.of(user));

        List<UserSec> userSecs = service.findAll();

        assertThat(userSecs).isNotNull();
        assertThat(userSecs.size()).isEqualTo(1);
    }

    @DisplayName("Test para obtener un user por ID")
    @Test
    public void findByIdTest(){
        given(repository.findById(user.getId())).willReturn(Optional.of(user));

        UserSec userGuardado = service.findById(user.getId()).get();

        assertThat(userGuardado).isNotNull();
    }

    @DisplayName("Test para actualizar un user")
    @Test
    public void updateTest(){
        given(repository.save(user)).willReturn(user);
        given(repository.findById(user.getId())).willReturn(Optional.of(user));
        user.setUsername("RodoMany");
        UserSec userGuardado  = service.update(user.getId(), user);
        assertThat(userGuardado).isNotNull();
        assertThat(userGuardado.getUsername()).isEqualTo("RodoMany");
    }

    @DisplayName("Test para eliminar un user")
    @Test
    public void deleteByIdTest(){

        willDoNothing().given(repository).deleteById(user.getId());

        service.deleteById(user.getId());

        verify(repository,times(1)).deleteById(user.getId());

        Optional<UserSec> UserSecOptional = repository.findById(user.getId());
        assertThat(UserSecOptional).isEmpty();
    }


}
