package com.example.Social_App.Model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_comment;
    @Lob
    private String content;
    @ManyToOne
    private User owner;

    @ManyToOne
    private Post post;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
