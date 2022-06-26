package com.example.Social_App.Model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_post;
    @Lob
    private String content;
    @ManyToOne
    private User owner;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
