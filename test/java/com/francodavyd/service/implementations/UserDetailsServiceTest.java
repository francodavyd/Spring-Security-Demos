package com.francodavyd.service.implementations;
import com.francodavyd.dto.AuthCreateRoleRequest;
import com.francodavyd.dto.AuthCreateUser;
import com.francodavyd.dto.AuthLoginRequestDTO;
import com.francodavyd.dto.AuthResponseDTO;
import com.francodavyd.model.Permission;
import com.francodavyd.model.Role;
import com.francodavyd.model.UserSec;
import com.francodavyd.repository.IRoleRepository;
import com.francodavyd.repository.IUserSecRepository;
import com.francodavyd.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {
    @Mock
    private IUserSecRepository repository;
    @Mock
    private JwtUtils utils;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private IRoleRepository roleRepository;
    @Mock
    private IUserSecRepository userSecRepository;
    @InjectMocks
    private UserDetailsServiceImpl service;
    private UserSec user;
    private AuthCreateUser createUser;

    @BeforeEach
    public void setup() {
        user = UserSec.builder()
                .username("GamaTest123")
                .password("123")
                .enabled(true)
                .accountNotExpired(true)
                .accountNotLocked(true)
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

    @DisplayName("Test para cargar un usuario por su nombre")
    @Test
    public void loadUserByUserNameTest() {
        given(userSecRepository.save(user)).willReturn(user);
        given(repository.findUserSecByUsername(user.getUsername())).willReturn(Optional.of(user));
        userSecRepository.save(user);
        UserDetails details = service.loadUserByUsername(user.getUsername());
        assertThat(details).isNotNull();
        assertThat(details.getUsername()).isEqualTo("GamaTest123");
    }

    @DisplayName("Test para comprobar la excepcion al no encontrar el usuario por su nombre")
    @Test
    public void loadUserByUserNameTestWithError() {
        given(repository.findUserSecByUsername(user.getUsername())).willReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> {
            service.loadUserByUsername(user.getUsername());
        });
    }

    @DisplayName("Test para el metodo de login")
    @Test
    public void loginUserTest() {
        AuthLoginRequestDTO request = new AuthLoginRequestDTO(user.getUsername(), "123");

        given(repository.findUserSecByUsername(user.getUsername())).willReturn(Optional.of(user));
        given(encoder.matches("123", user.getPassword())).willReturn(true);
        given(utils.createToken(any(Authentication.class))).willReturn("thisjwttoken");

        AuthResponseDTO responseDTO = service.loginUser(request);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.username()).isEqualTo(user.getUsername());

    }

    @DisplayName("Test para el metodo authenticate")
    @Test
    public void authenticateTest() {
        given(userSecRepository.save(user)).willReturn(user);
        given(repository.findUserSecByUsername(user.getUsername())).willReturn(Optional.of(user));
        userSecRepository.save(user);
        given(encoder.matches("123", user.getPassword())).willReturn(true);
        Authentication authentication = service.authenticate(user.getUsername(), user.getPassword());

        assertThat(authentication).isNotNull();
    }

    @DisplayName("Test para el metodo authenticated pero pasando credenciales invalidas")
    @Test
    public void authenticateTestWithBadCredentialsException1() {
        given(repository.findUserSecByUsername(user.getUsername())).willReturn(Optional.ofNullable(user));

        assertThrows(BadCredentialsException.class, () -> {
            service.authenticate(user.getUsername(), user.getPassword());
        });
    }

    @DisplayName("Test para el metodo authenticated pero pasando contraseña invalida")
    @Test
    public void authenticateTestWithBadCredentialsException2() {
        given(repository.findUserSecByUsername(user.getUsername())).willReturn(Optional.of(user));
        given(encoder.matches(user.getPassword(), user.getPassword())).willReturn(false);
        assertThrows(BadCredentialsException.class, () -> {
            service.authenticate(user.getUsername(), user.getPassword());
        });

    }

    @DisplayName("Test para crear usuarios")
    @Test
    public void createUserTest() {
        String encodedPassword = user.getPassword();
        given(encoder.encode(encodedPassword)).willReturn(encodedPassword);

        createUser = AuthCreateUser.builder()
                .username(user.getUsername())
                .password(encodedPassword)
                .authCreateRoleRequest(new AuthCreateRoleRequest(List.of("ADMIN")))
                .build();

        given(roleRepository.findByRoleIn(createUser.authCreateRoleRequest().roleListName())).willReturn(new ArrayList<>(Arrays.asList(
                Role.builder()
                        .role("USER")
                        .permissionsList(new HashSet<>(Arrays.asList(
                                Permission.builder()
                                        .permissionName("READ")
                                        .build()
                        )))
                        .build()
        )));

        given(repository.save(any(UserSec.class))).willAnswer(invocation -> invocation.getArgument(0));


        given(utils.createToken(any(Authentication.class))).willReturn("thisjwttoken");


        AuthResponseDTO responseDTO = service.createUser(createUser);

        assertThat(responseDTO).isNotNull();
    }
    @DisplayName("Test para crear usuarios pero con error")
    @Test
    public void createUserTestWithError() {
        given(roleRepository.findByRoleIn(anyList())).willReturn(Collections.emptyList());

        assertThrows(IllegalArgumentException.class, () -> {
            service.createUser(new AuthCreateUser("Franco", "Sanchez", new AuthCreateRoleRequest(Collections.emptyList())));
        });
    }
}
