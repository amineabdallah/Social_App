package com.example.Social_App.DTO;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {

    private Long id_user;

    private String firstname;
    private String lastname;
    private String email;
    private String username;


}
