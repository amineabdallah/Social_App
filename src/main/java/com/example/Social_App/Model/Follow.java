package com.example.Social_App.Model;

import lombok.*;

import javax.persistence.*;

@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Follow;
    @ManyToOne
    private User this_One;
    @ManyToOne
    private User to_This;

}
