<<<<<<< HEAD
package com.francodavyd.service.implementations;

import com.francodavyd.dto.AuthorDTO;
import com.francodavyd.dto.PostDTO;
import com.francodavyd.model.Author;
import com.francodavyd.model.Post;
import com.francodavyd.repository.IPostRepository;

import com.francodavyd.service.services.IAuthorService;
import com.francodavyd.service.services.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PostServiceImpl implements IPostService {
    @Autowired
    private IPostRepository repository;
    @Autowired
    private IAuthorService serviceA;
    @Override
    @Transactional
    public Post save(PostDTO post) {
        Set<AuthorDTO> authorsIds = post.getAuthorsList();
        Set<Author> listAuthors = new HashSet<>();

        for (AuthorDTO authorDTO : authorsIds) {
            Optional<Author> getAuthor = serviceA.findById(authorDTO.getId());
            getAuthor.ifPresent(listAuthors::add);
        }

        Post newPost = Post.builder()
                .title(post.getTitle())
                .description(post.getDescription())
                .creationDate(LocalDate.now())
                .authorsList(listAuthors)
                .build();

        repository.save(newPost);

        for (Author author : listAuthors) {
            Set<Post> posts = new HashSet<>(author.getPostsList());
            posts.add(newPost);
            author.setPostsList(posts);
            serviceA.update(author.getId(), author);
        }

        return newPost;
    }
    @Override
    public List<Post> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
       repository.deleteById(id);
    }

    @Override
    public Post update(Long id, Post updatedPost) {
        Optional<Post> searchedPost = this.findById(id);
        if (searchedPost.isPresent()) {
            Post post = searchedPost.get();
            post.setTitle(updatedPost.getTitle());
            post.setDescription(updatedPost.getDescription());
            post.setAuthorsList(updatedPost.getAuthorsList());
            return repository.save(post);
        }
        return null;
    }
}
=======
package com.francodavyd.service.implementations;

import com.francodavyd.dto.AuthorDTO;
import com.francodavyd.dto.PostDTO;
import com.francodavyd.model.Author;
import com.francodavyd.model.Post;
import com.francodavyd.repository.IPostRepository;

import com.francodavyd.service.services.IAuthorService;
import com.francodavyd.service.services.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PostServiceImpl implements IPostService {
    @Autowired
    private IPostRepository repository;
    @Autowired
    private IAuthorService serviceA;
    @Override
    @Transactional
    public Post save(PostDTO post) {
    Set<AuthorDTO> authorsIds = post.getAuthorsList();
    Optional<Author> getAuthor = Optional.empty();
    Set<Author> listAuthors = new HashSet<>();

    for (AuthorDTO author : authorsIds){
        getAuthor = serviceA.findById(author.getId());
        if (getAuthor.isPresent()){
         listAuthors.add(getAuthor.get());
        }
    }

    Post newPost = Post.builder()
            .title(post.getTitle())
            .description(post.getDescription())
            .creationDate(LocalDate.now())
            .authorsList(listAuthors)
            .build();
    repository.save(newPost);

        for (Author author : listAuthors){
            Set<Post> posts = author.getPostsList();
            posts.add(newPost);
            author.setPostsList(posts);
            serviceA.update(author.getId(), author);
        }

    return newPost;
    }

    @Override
    public List<Post> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
       repository.deleteById(id);
    }

    @Override
    public Post update(Long id, Post updatedPost) {
        Optional<Post> searchedPost = this.findById(id);
        if (searchedPost.isPresent()) {
            Post post = searchedPost.get();
            post.setTitle(updatedPost.getTitle());
            post.setDescription(updatedPost.getDescription());
            post.setAuthorsList(updatedPost.getAuthorsList());
            return repository.save(post);
        }
        return null;
    }
}
>>>>>>> 1da008a75635c65c3167e712ecb49d3a2672cf0c
