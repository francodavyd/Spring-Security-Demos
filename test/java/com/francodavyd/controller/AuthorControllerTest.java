package com.francodavyd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.francodavyd.model.Author;
import com.francodavyd.service.services.IAuthorService;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IAuthorService service;
    @Autowired
    private ObjectMapper mapper;
    private Author author;
    private String tokenTest;

    @BeforeEach
    public void setup() throws Exception {
        author = Author.builder()
                .id(1L)
                .name("Pablo")
                .lastName("De Tarso")
                .postsList(Collections.emptySet())
                .build();
        tokenTest = loguearUsuarioYGenerarJwtToken();

    }
    @DisplayName("Test para guardar un autor")
    @Test
    public void saveTest() throws Exception {

        given(service.save(any(Author.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/author/save")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + tokenTest)
                .content(mapper.writeValueAsString(author)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(author.getName())))
                .andExpect(jsonPath("$.lastName", is(author.getLastName())));
    }
    @DisplayName("Test para obtener lista de autores")
    @Test
    public void getAllTest() throws Exception {
        List<Author> list = List.of(author);
        given(service.findAll()).willReturn(list);
        ResultActions response = mockMvc.perform(get("/api/author/all")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + tokenTest)
                .content(mapper.writeValueAsString(list)));
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(list.size())));


    }
    @DisplayName("Test para obtener un autor por id")
    @Test
    public void findByIdTest() throws Exception {
        given(service.findById(author.getId())).willReturn(Optional.of(author));
        ResultActions response = mockMvc.perform(get("/api/author/get/{id}",author.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer " + tokenTest)
                .content(mapper.writeValueAsString(author))
        );
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(author.getName())));
    }
    @DisplayName("Test para eliminar un autor por id")
    @Test
    public void deleteById() throws Exception {
        willDoNothing().given(service).deleteById(author.getId());
        ResultActions response = mockMvc.perform(delete("/api/author/delete/{id}",author.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + tokenTest));
        response.andExpect(status().isOk());

    }
    @DisplayName("Test para editar un autor")
    @Test
    public void update() throws Exception {
        Author authorActualizado = Author.builder()
                .id(1L)
                .name("Diego")
                .lastName("Mora")
                .postsList(Collections.emptySet())
                .build();

        given(service.findById(author.getId())).willReturn(Optional.of(author));
        given(service.update(any(Long.class), any(Author.class))).willReturn(authorActualizado);
        ResultActions response = mockMvc.perform(put("/api/author/edit/{id}",author.getId())
                .header("Authorization","Bearer " +tokenTest)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(authorActualizado)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(authorActualizado.getName())))
                .andExpect(jsonPath("$.lastName",is(authorActualizado.getLastName())));;

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
