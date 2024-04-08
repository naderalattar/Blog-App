package com.springboot.blog.payload;

import com.springboot.blog.model.Comment;
import com.springboot.blog.model.Post;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostsDTO {

    private Long id;

    @NotNull
    @Size(min = 2,message = "title have to be more than 2 character")
    private String title;

    @NotNull
    @Size(min = 10,message = "description have to be more than 10 character")
    private String description;

    @NotNull
    @Size(min = 20,message = "description have to be more than 20 character")
    private String contant;

    private List<CommentDTO> comment;


}
