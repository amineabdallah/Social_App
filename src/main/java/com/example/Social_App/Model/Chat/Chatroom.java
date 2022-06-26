package com.example.Social_App.Model.Chat;

import com.example.Social_App.Model.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Chatroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_chatroom;
    @ManyToOne
    private User Sender_id;
    @ManyToOne
    private User reciever_id;

}
