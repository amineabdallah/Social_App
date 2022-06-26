package com.example.Social_App.DTO;


import com.example.Social_App.Model.ReactType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReactDTO {

    private Long id_react;

    @Enumerated(EnumType.STRING)
    private ReactType reactType;

    private UserDTO owner;

    private PostDTO post;

    private CommentDTO comment;



}
