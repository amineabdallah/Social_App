package com.example.Social_App.Mapper;

import com.example.Social_App.DTO.CommentDTO;
import com.example.Social_App.Model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {

    public static final CommentMapper MAPPER = Mappers.getMapper(CommentMapper.class);

    public abstract CommentDTO commentToDto(Comment comment);

    public abstract Comment dtoTocomment(CommentDTO commentDTO);
}
