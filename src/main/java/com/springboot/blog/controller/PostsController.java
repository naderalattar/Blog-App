package com.springboot.blog.controller;

import com.springboot.blog.payload.PostsDTO;
import com.springboot.blog.payload.PostsResponse;
import com.springboot.blog.service.PostsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@SecurityRequirement(
        name = "Bearer Authentication"
)
@Tag(
        name = "Post REST APIs"
)
public class PostsController {


    private PostsService postsService;

    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }


    @Operation(
            summary = "create post REST API"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 201 created "

    )
    @PostMapping
    public ResponseEntity<PostsDTO> create(@Valid @RequestBody PostsDTO postsDTO){
        return new ResponseEntity<>(postsService.create(postsDTO), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Get All Posts REST API"
    )
    @GetMapping
    public  ResponseEntity<PostsResponse> getAllPosts(
            @RequestParam(name="pageNumber",required = false,defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize",required = false,defaultValue = "5") int pageSize,
            @RequestParam(name = "sortBy",required = false,defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDirection",required = false,defaultValue = "asc") String sortDirection
    ){

        return new ResponseEntity<>(postsService.getAll(pageNumber,pageSize,sortBy,sortDirection),HttpStatus.OK);
    }


    @Operation(
            summary = "Get Post By Id REST API"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostsDTO> getById(@PathVariable Long id){
        return new ResponseEntity<>(postsService.getById(id),HttpStatus.OK);
    }


    @Operation(
            summary = "Update post REST API"
    )
    @PutMapping
    public ResponseEntity<PostsDTO> update(@Valid @RequestBody PostsDTO postsDTO){
        return new ResponseEntity<>(postsService.update(postsDTO),HttpStatus.OK);
    }


    @Operation(
            summary = "Delete Post By Id REST API"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        postsService.delete(id);
        return new ResponseEntity<>("post is deleted succesfuly",HttpStatus.OK);
    }

    @Operation(
            summary = "Get Posts By Category REST API"
    )
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostsDTO>> getPostsByCategory(@PathVariable("id") Long categoryId){
        List<PostsDTO> postDtos = postsService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(postDtos);
    }
}
