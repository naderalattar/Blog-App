package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostsResponse {

    private List<PostsDTO> contant;

    private int pageNumber;

    private int pageSize;

    private int totalPages;

    private  Long totaElements;

    private boolean isLast;


}
