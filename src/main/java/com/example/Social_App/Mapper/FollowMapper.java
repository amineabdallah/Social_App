package com.example.Social_App.Mapper;

import com.example.Social_App.DTO.FollowDTO;
import com.example.Social_App.Model.Follow;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class FollowMapper {
    public static final FollowMapper MAPPER = Mappers.getMapper(FollowMapper.class);

    public abstract FollowDTO followToDto(Follow follow);

    public abstract Follow dtoTofollow(FollowDTO followDTO);
}
