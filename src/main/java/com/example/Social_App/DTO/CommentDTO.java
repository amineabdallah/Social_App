package com.example.Social_App.DTO;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentDTO {

    private Long id_comment;

    private String content;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private PostDTO post;

    private UserDTO owner;


}
