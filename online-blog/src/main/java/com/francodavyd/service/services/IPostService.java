package com.francodavyd.service.services;

import com.francodavyd.dto.PostDTO;
import com.francodavyd.model.Post;

import java.util.List;
import java.util.Optional;

public interface IPostService {
    public Post save(PostDTO post);
    public List<Post> findAll();
    public Optional<Post> findById(Long id);
    public void deleteById(Long id);
    public Post update(Long id, Post updatedPost);
}
