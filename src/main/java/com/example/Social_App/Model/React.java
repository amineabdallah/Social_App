package com.example.Social_App.Model;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class React implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_react;
    @ManyToOne
    private User owner;

    @ManyToOne
    private Post post;

    @ManyToOne
    private Comment comment;

    @Enumerated(EnumType.STRING)
    private ReactType reactType;

}
