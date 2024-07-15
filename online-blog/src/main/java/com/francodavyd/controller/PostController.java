package com.francodavyd.controller;

import com.francodavyd.dto.PostDTO;
import com.francodavyd.model.Post;
import com.francodavyd.service.services.IPostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@PreAuthorize("denyAll()")
public class PostController {
    @Autowired
    private IPostService service;
    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('CREATE')")
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDTO newPost){
        try {
            return new ResponseEntity<>(service.save(newPost), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>("Error, " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('READ')")
    public ResponseEntity<?> getAll (){
        try {
            return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Error, " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('READ')")
    public ResponseEntity<?> getPost(@PathVariable Long id){
        try {
            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("The requested post has not been found: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        try {
            service.deleteById(id);
            return new ResponseEntity<>("Post successfully deleted", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Error, " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('UPDATE')")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Post updatedPost){
        try {
            return new ResponseEntity<>(service.update(id,updatedPost), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Error, " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
