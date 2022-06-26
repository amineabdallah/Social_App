package com.example.Social_App.Mapper;

import com.example.Social_App.DTO.PostDTO;

import com.example.Social_App.Model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class PostMapper {


    public static final PostMapper MAPPER = Mappers.getMapper(PostMapper.class);

    public abstract PostDTO postToDto(Post post);

    public abstract Post dtoToPost(PostDTO postDTO);
}
