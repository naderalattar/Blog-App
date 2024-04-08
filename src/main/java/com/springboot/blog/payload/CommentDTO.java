package com.springboot.blog.payload;


import com.springboot.blog.validation.PhoneNumberConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Long id;

    @NotNull(message = "name should not be null")
    @NotEmpty(message = "name should be not empty")
    private String name;

    @NotNull(message = "body should not be null")
    @NotEmpty(message = "body should be not empty")
    @Size(min = 10,message = "body shoud contain at lest 10 characters")
    private String body;


//    @Email
    @NotNull(message = "email should not be null")
    @NotEmpty(message = "email should be not empty")
    @PhoneNumberConstraint
    private String email;


}
