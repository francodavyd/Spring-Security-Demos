package com.francodavyd.service.services;

import com.francodavyd.model.Author;

import java.util.List;
import java.util.Optional;

public interface IAuthorService {
    public Author save(Author author);
    public List<Author> findAll();
    public Optional<Author>findById(Long id);
    public void deleteById(Long id);
    public Author update(Long id, Author updatedAuthor);
}
