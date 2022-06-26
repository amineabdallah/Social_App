package com.example.Social_App.DTO;


import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDTO {

    private Long id_post;

    private String content;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UserDTO owner;





}
