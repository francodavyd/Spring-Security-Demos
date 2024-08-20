package com.francodavyd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.francodavyd.dto.AuthorDTO;
import com.francodavyd.dto.PostDTO;
import com.francodavyd.model.Author;
import com.francodavyd.model.Post;
import com.francodavyd.service.services.IAuthorService;
import com.francodavyd.service.services.IPostService;
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
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IPostService service;
    @MockBean
    private IAuthorService serviceA;
    @Autowired
    private ObjectMapper mapper;
    private Post post;
    private String tokenTest;
    private Author author;
    private PostDTO postDTO;
    @BeforeEach
    public void setup() throws Exception {
        author = Author.builder()
                .id(1L)
                .name("Pablo")
                .lastName("Asturia")
                .postsList(Collections.emptySet())
                .build();

        post = Post.builder()
                .id(2L)
                .title("Harry Potter")
                .description("Harry Potter game guides")
                .creationDate(LocalDate.now())
                .authorsList(new HashSet<>(Arrays.asList(
                        author
                )))
                .build();
        tokenTest = loguearUsuarioYGenerarJwtToken();
    }
    @DisplayName("Test para guardar un post")
    @Test
    public void saveTest() throws Exception{
        postDTO = new PostDTO(post.getTitle(), post.getDescription(), new HashSet<>(Arrays.asList(new AuthorDTO(author.getId()))));
        given(serviceA.findById(author.getId())).willReturn(Optional.of(author));
        given(serviceA.update(any(Long.class), any(Author.class))).willAnswer(invocationOnMock -> invocationOnMock.getClass());

        ResultActions response = mockMvc.perform(post("/api/post/save")
                .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " +tokenTest)
                .content(mapper.writeValueAsString(postDTO)));

        response.andExpect(status().isCreated())
                .andDo(print());
    }
    @DisplayName("Test para obtener una lista de posts")
    @Test
    public void getAllTest() throws Exception{
        List<Post> posts = List.of(post);
        given(service.findAll()).willReturn(posts);
        ResultActions response = mockMvc.perform(get("/api/post/all")
                .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer " + tokenTest)
                .content(mapper.writeValueAsString(posts)));
       response.andExpect(jsonPath("$.size()",is(1)))
               .andExpect(status().isOk());
    }
    @DisplayName("Test para obtener un post por id")
    @Test
    public void findByIdTest() throws Exception{
        given(service.findById(post.getId())).willReturn(Optional.of(post));
        ResultActions response = mockMvc.perform(get("/api/post/get/{id}", post.getId())
                        .header("Authorization", "Bearer " +tokenTest)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(post)));
        response.andExpect(jsonPath("$.description", is(post.getDescription())))
                .andExpect(status().isOk());

    }
    @DisplayName("Test para eliminar un post por id")
    @Test
    public void deleteByIdTest() throws Exception{
        willDoNothing().given(service).deleteById(post.getId());
        ResultActions response = mockMvc.perform(delete("/api/post/delete/{id}",post.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + tokenTest));
        response.andExpect(status().isOk());
    }
    @DisplayName("Test para editar un post")
    @Test
    public void updateTest() throws Exception{
        Post postActualizado = Post.builder()
                .id(2L)
                .title("Hello Wave")
                .description("No description")
                .creationDate(LocalDate.now())
                .authorsList(Collections.emptySet())
                .build();

        given(service.findById(post.getId())).willReturn(Optional.of(post));
        given(service.update(any(Long.class), any(Post.class))).willReturn(postActualizado);
        ResultActions response = mockMvc.perform(put("/api/post/edit/{id}",post.getId())
                .header("Authorization","Bearer " +tokenTest)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(postActualizado)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title",is(postActualizado.getTitle())))
                .andExpect(jsonPath("$.description",is(postActualizado.getDescription())));
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
