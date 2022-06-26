package com.example.Social_App.Mapper;

import com.example.Social_App.DTO.UserCreationDTO;
import com.example.Social_App.Model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public abstract class UserCreationMapper {

    public static final UserCreationMapper MAPPER = Mappers.getMapper(UserCreationMapper.class);

    public abstract UserCreationDTO userToDto(User user);

    public abstract User dtoToUser(UserCreationDTO usercDTO);
}
