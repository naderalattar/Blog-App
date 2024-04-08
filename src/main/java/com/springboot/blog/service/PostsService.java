package com.springboot.blog.service;

import com.springboot.blog.payload.PostsDTO;
import com.springboot.blog.payload.PostsResponse;

import java.util.List;

public interface PostsService {

    PostsDTO create(PostsDTO postsDTO);

    PostsResponse getAll(int pageNumber, int pageSize, String sortBy, String sortDirection);

    PostsDTO getById(Long id);

    PostsDTO update(PostsDTO postsDTO);

    void delete(Long id);

    List<PostsDTO> getPostsByCategory(Long categoryId);
}
