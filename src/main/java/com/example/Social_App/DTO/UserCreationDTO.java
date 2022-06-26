package com.example.Social_App.DTO;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCreationDTO {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String username;


}
