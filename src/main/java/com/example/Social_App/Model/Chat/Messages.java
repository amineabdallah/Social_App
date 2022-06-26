package com.example.Social_App.Model.Chat;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_message;
    @ManyToOne
    private Chatroom chatroom_id;

    private String content;

    private LocalDateTime sended_At;

    private String statut;
}
