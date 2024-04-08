package com.springboot.blog.service.impl;

import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.Category;
import com.springboot.blog.model.Comment;
import com.springboot.blog.model.Post;
import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.payload.PostsDTO;
import com.springboot.blog.payload.PostsResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostsService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostsServiceImpl  implements PostsService {

    private PostRepository postRepository;

    private ModelMapper modelMapper;

    private CategoryRepository categoryRepository;

    public PostsServiceImpl(PostRepository postRepository, ModelMapper mapper,
                           CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostsDTO create(PostsDTO postsDTO) {

        Post post=mapToPost(postsDTO);
        Post savedPost=postRepository.save(post);
        PostsDTO newPostDTO=mapToDTO(savedPost);

        return newPostDTO;
    }

    @Override
    public PostsResponse getAll(int pageNumber, int pageSize, String sortBy, String sortDirection) {

        Sort sort=sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();

        Pageable pageable= PageRequest.of(pageNumber,pageSize, sort);

        Page<Post> page=postRepository.findAll(pageable);
        List<Post> posts=page.getContent();

        List<PostsDTO> postsDTOS = posts.stream().map(post -> mapToDTO(post)).toList();

        PostsResponse postsResponse=new PostsResponse();
        postsResponse.setContant(postsDTOS);
        postsResponse.setPageNumber(page.getNumber());
        postsResponse.setPageSize(page.getSize());
        postsResponse.setTotalPages(page.getTotalPages());
        postsResponse.setTotaElements(page.getTotalElements());
        postsResponse.setLast(page.isLast());


        return postsResponse;
    }

    @Override
    public PostsDTO getById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Post" ,"id" ,String.valueOf(id)));
        return mapToDTO(post);
    }

    @Override
    public PostsDTO update(PostsDTO postsDTO) {

        Post post = postRepository.findById(postsDTO.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Post" ,"id" ,String.valueOf(postsDTO.getId())));

        post.setTitle(postsDTO.getTitle());
        post.setDescription(postsDTO.getDescription());
        post.setContant(postsDTO.getContant());

        Post savedPost=postRepository.save(post);
        return mapToDTO(savedPost);
    }

    @Override
    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post" ,"id" ,String.valueOf(id)));
        postRepository.deleteById(id);
    }

    @Override
    public List<PostsDTO> getPostsByCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", String.valueOf(categoryId)));

        List<Post> posts = postRepository.findByCategoryId(categoryId);

        return posts.stream().map((post) -> mapToDTO(post))
                .collect(Collectors.toList());
    }


    private PostsDTO mapToDTO(Post post){
        PostsDTO postsDTO=modelMapper.map(post,PostsDTO.class);
        return postsDTO;
    }


    private Post mapToPost(PostsDTO postsDTO){
        Post post=modelMapper.map(postsDTO,Post.class);
        return post;
    }


}
