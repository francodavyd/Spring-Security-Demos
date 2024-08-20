package com.francodavyd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.francodavyd.model.Permission;
import com.francodavyd.model.Role;
import com.francodavyd.service.services.IRoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IRoleService service;
    @Autowired
    private ObjectMapper mapper;
    private Role role;
    private String tokenTest;

    @BeforeEach
    public void setup() throws Exception {
        role = Role.builder()
                .id(1L)
                .role("ADMIN")
                .permissionsList(new HashSet<>(Arrays.asList(Permission.builder().id(2L).permissionName("CREATE").build())))
                .build();
        tokenTest = loguearUsuarioYGenerarJwtToken();

    }
    @DisplayName("Test para guardar un rol")
    @Test
    public void saveTest() throws Exception {

        given(service.save(any(Role.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/role/save")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + tokenTest)
                .content(mapper.writeValueAsString(role)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.role", is(role.getRole())));
    }
    @DisplayName("Test para obtener lista de roles")
    @Test
    public void getAllTest() throws Exception {
        List<Role> list = List.of(role);
        given(service.findAll()).willReturn(list);
        ResultActions response = mockMvc.perform(get("/api/role/all")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + tokenTest)
                .content(mapper.writeValueAsString(list)));
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(list.size())));
    }
    @DisplayName("Test para obtener un rol por id")
    @Test
    public void findByIdTest() throws Exception {
        given(service.findById(role.getId())).willReturn(Optional.of(role));
        ResultActions response = mockMvc.perform(get("/api/role/get/{id}", role.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer " + tokenTest)
                .content(mapper.writeValueAsString(role))
        );
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role",is(role.getRole())));
    }

    @DisplayName("Test para editar un rol")
    @Test
    public void update() throws Exception {
        Role rolActualizado = Role.builder()
                .role("MANAGER")
                .permissionsList(Collections.emptySet())
                .build();

        given(service.findById(role.getId())).willReturn(Optional.of(role));
        given(service.update(any(Long.class), any(Role.class))).willReturn(rolActualizado);

        ResultActions response = mockMvc.perform(patch("/api/role/edit/{id}", role.getId())
                .header("Authorization","Bearer " +tokenTest)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(rolActualizado)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role",is(rolActualizado.getRole())));

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
