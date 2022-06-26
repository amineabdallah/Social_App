package com.example.Social_App.Mapper;

import com.example.Social_App.DTO.UserDTO;
import com.example.Social_App.Model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public static final UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    public abstract UserDTO userToDto(User user);

    public abstract User dtoToUser(UserDTO userDTO);
}
