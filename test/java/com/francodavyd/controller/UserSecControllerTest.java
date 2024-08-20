package com.francodavyd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.francodavyd.model.Permission;
import com.francodavyd.model.Role;
import com.francodavyd.model.UserSec;
import com.francodavyd.service.services.IRoleService;
import com.francodavyd.service.services.IUserSecService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.hamcrest.CoreMatchers.is;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserSecControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IUserSecService service;
    @MockBean
    private IRoleService serviceRole;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ObjectMapper mapper;
    private UserSec sec;
    private Role role;
    private String tokenTest;
    @BeforeEach
    public void setup() throws Exception {
        role =   Role.builder()
                .id(2L)
                .role("USER")
                .permissionsList(new HashSet<>(Arrays.asList(
                        Permission.builder()
                                .permissionName("READ")
                                .build()
                )))
                .build();
        sec = UserSec.builder()
                .id(1L)
                .username("GamaTest123")
                .password("examplepassword")
                .enabled(true)
                .accountNotLocked(true)
                .accountNotExpired(true)
                .credentialNotExpired(true)
                .rolesList(new HashSet<>(Arrays.asList(
                      role
                )))
                .build();
        tokenTest = loguearUsuarioYGenerarJwtToken();
    }
    @DisplayName("Test para crear un usuario")
    @Test
    public void saveTest() throws Exception{
        given(service.encriptPassword(sec.getPassword())).willReturn(encoder.encode(sec.getPassword()));
        given(serviceRole.findById(role.getId())).willReturn(Optional.of(role));
        given(service.save(any(UserSec.class))).willReturn(sec);

        ResultActions response = mockMvc.perform(post("/api/users/save")
                .content(mapper.writeValueAsString(sec))
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(print()).andExpect(status().isCreated());
    }
    @DisplayName("Test para obtener una lista de usuarios")
    @Test
    public void getAllTest() throws Exception{
        List<UserSec> list = List.of(sec);
        given(service.findAll()).willReturn(list);
        ResultActions response = mockMvc.perform(get("/api/users/all")
                .content(mapper.writeValueAsString(list))
                        .header("Authorization","Bearer " + tokenTest)
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk()).andExpect(jsonPath("$.size()",is(list.size())));
    }
    @DisplayName("Test para obtener un usuario por id")
    @Test
    public void findByIdTest() throws Exception{
        given(service.findById(sec.getId())).willReturn(Optional.of(sec));
        ResultActions response = mockMvc.perform(get("/api/users/get/{id}", sec.getId())
                .content(mapper.writeValueAsString(sec))
                .header("Authorization","Bearer " + tokenTest)
                .contentType(MediaType.APPLICATION_JSON));
        response.andDo(print()).andExpect(status().isOk());
    }
    @DisplayName("Test para eliminar un usuario")
    @Test
    public void deleteByIdTest() throws Exception{
        willDoNothing().given(service).deleteById(sec.getId());
        ResultActions response = mockMvc.perform(delete("/api/users/delete/{id}", sec.getId())
                .content(mapper.writeValueAsString(sec))
                .header("Authorization","Bearer " + tokenTest)
                .contentType(MediaType.APPLICATION_JSON));
        response.andDo(print()).andExpect(status().isOk());
    }
    @DisplayName("Test para editar un usuario")
    @Test
    public void updateTest() throws Exception{
        given(service.findById(sec.getId())).willReturn(Optional.of(sec));
       UserSec secActualizado = UserSec.builder()
                .id(1L)
                .username("GamaTest2020")
                .password("voidpassword")
                .enabled(true)
                .accountNotLocked(true)
                .accountNotExpired(true)
                .credentialNotExpired(true)
                .rolesList(new HashSet<>(Arrays.asList(
                        role
                )))
                .build();
        given(service.update(any(Long.class), any(UserSec.class))).willReturn(secActualizado);
        ResultActions response = mockMvc.perform(put("/api/users/edit/{id}", sec.getId())
                .header("Authorization","Bearer " +tokenTest)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(secActualizado)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username",is(secActualizado.getUsername())));
    }
    private String loguearUsuarioYGenerarJwtToken() throws Exception {
        String response = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"admin\",\"password\":\"admin\"}"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return mapper.readTree(response).get("jwt").asText();
    }

}
