package com.francodavyd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.francodavyd.dto.AuthCreateRoleRequest;
import com.francodavyd.dto.AuthCreateUser;
import com.francodavyd.dto.AuthLoginRequestDTO;
import com.francodavyd.dto.AuthResponseDTO;
import com.francodavyd.service.implementations.UserDetailsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.given;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private ObjectMapper mapper;
    @DisplayName("Test para registrar un usuario")
    @Test
    public void registerTest() throws Exception {
        AuthCreateUser createUserRequest = new AuthCreateUser("username", "password", new AuthCreateRoleRequest(List.of("ADMIN")));
        AuthResponseDTO responseDTO = new AuthResponseDTO(createUserRequest.username(), createUserRequest.password(), "thisjwt",true);

        given(userDetailsService.createUser(createUserRequest)).willReturn(responseDTO);

        ResultActions response = mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createUserRequest)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.username",is(responseDTO.username())))
                .andExpect(jsonPath("$.jwt",is(responseDTO.jwt())));
    }
    @DisplayName("Test para iniciar sesion")
    @Test
    public void loginTest() throws Exception{
        AuthLoginRequestDTO authLogin = new AuthLoginRequestDTO("admin","admin");
        AuthResponseDTO responseDTO = new AuthResponseDTO(authLogin.username(), authLogin.password(), "thisjwt",true);

        given(userDetailsService.loginUser(authLogin)).willReturn(responseDTO);

        ResultActions response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(authLogin)));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.username",is(responseDTO.username())))
                .andExpect(jsonPath("$.jwt",is(responseDTO.jwt())));
    }

}
