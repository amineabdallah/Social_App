package com.example.Social_App.DTO;

import com.example.Social_App.Model.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FollowDTO {

    private Long id_Follow;

    private User this_One;

    private User to_This;
}
