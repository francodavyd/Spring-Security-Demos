package com.francodavyd.service.implementations;

import com.francodavyd.model.Author;
import com.francodavyd.model.Post;
import com.francodavyd.repository.IAuthorRepository;
import com.francodavyd.service.services.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements IAuthorService {
    @Autowired
    private IAuthorRepository repository;

    @Override
    public Author save(Author author) {
        return repository.save(author);
    }

    @Override
    public List<Author> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
    repository.deleteById(id);
    }

    @Override
    public Author update(Long id, Author updatedAuthor) {
        Optional<Author> searchedAuthor = this.findById(id);
        if (searchedAuthor.isPresent()) {
            Author author = searchedAuthor.get();
            author.setName(updatedAuthor.getName());
            author.setLastName(updatedAuthor.getLastName());
            author.setPostsList(updatedAuthor.getPostsList());
            return repository.save(author);
        }
        return null;
    }
}
