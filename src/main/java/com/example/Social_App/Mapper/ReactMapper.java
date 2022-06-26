package com.example.Social_App.Mapper;

import com.example.Social_App.DTO.ReactDTO;
import com.example.Social_App.Model.React;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public abstract class ReactMapper {

    public static final ReactMapper MAPPER = Mappers.getMapper(ReactMapper.class);

    public abstract ReactDTO reactToDto(React react);

    public abstract React dtoToReact(ReactDTO reactDTO);
}
