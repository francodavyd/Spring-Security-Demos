package com.francodavyd.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.francodavyd.model.Permission;
import com.francodavyd.service.services.IPermissionService;
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
import java.util.List;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PermissionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IPermissionService service;
    @Autowired
    private ObjectMapper mapper;
    private Permission permission;
    private String tokenTest;
    @BeforeEach
    public void setup() throws Exception {
        permission= Permission.builder()
                .id(1L)
                .permissionName("CREATE")
                .build();
        tokenTest = loguearUsuarioYGenerarJwtToken();
    }
    @DisplayName("Test para guardar un permiso")
    @Test
    public void saveTest() throws Exception {
        given(service.save(any(Permission.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/permission/save")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + tokenTest)
                .content(mapper.writeValueAsString(permission)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.permissionName", is(permission.getPermissionName())));
    }
    @DisplayName("Test para obtener lista de permisos")
    @Test
    public void getAllTest() throws Exception {
        List<Permission> list = List.of(permission);
        given(service.findAll()).willReturn(list);
        ResultActions response = mockMvc.perform(get("/api/permission/all")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + tokenTest)
                .content(mapper.writeValueAsString(list)));
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(list.size())));
    }
    @DisplayName("Test para obtener un permiso por id")
    @Test
    public void findByIdTest() throws Exception {
        given(service.findById(permission.getId())).willReturn(Optional.of(permission));
        ResultActions response = mockMvc.perform(get("/api/permission/get/{id}",permission.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer " + tokenTest)
                .content(mapper.writeValueAsString(permission))
        );
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.permissionName",is(permission.getPermissionName())));
    }
    @DisplayName("Test para eliminar un permiso por id")
    @Test
    public void deleteById() throws Exception {
        willDoNothing().given(service).deleteById(permission.getId());
        ResultActions response = mockMvc.perform(delete("/api/permission/delete/{id}",permission.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + tokenTest));
        response.andExpect(status().isOk());

    }
    @DisplayName("Test para editar un permiso")
    @Test
    public void update() throws Exception {
        Permission permisoActualizado = Permission.builder()
                .permissionName("READ")
                .build();
        given(service.findById(permission.getId())).willReturn(Optional.of(permission));
        given(service.update(any(Long.class), any(Permission.class))).willReturn(permisoActualizado);
        ResultActions response = mockMvc.perform(put("/api/permission/edit/{id}",permission.getId())
                .header("Authorization","Bearer " +tokenTest)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(permisoActualizado)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.permissionName",is(permisoActualizado.getPermissionName())));

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
