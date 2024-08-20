package com.francodavyd.controller;

import com.francodavyd.model.Author;
import com.francodavyd.model.Post;
import com.francodavyd.service.services.IAuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/author")
@PreAuthorize("denyAll()")
public class AuthorController {
    @Autowired
    private IAuthorService service;
    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createAuthor(@Valid @RequestBody Author newAuthor){
        try {
            return new ResponseEntity<>(service.save(newAuthor), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>("Error, " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAll (){
        try {
            return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Error, " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAuthor(@PathVariable Long id){
        try {
            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("The requested author has not been found: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id){
        try {
            service.deleteById(id);
            return new ResponseEntity<>("Author successfully deleted", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Error, " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateAuthor(@PathVariable Long id, @RequestBody Author updatedAuthor){
        try {
            return new ResponseEntity<>(service.update(id,updatedAuthor), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Error, " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
