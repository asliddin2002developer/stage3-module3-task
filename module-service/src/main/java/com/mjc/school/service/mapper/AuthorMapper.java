package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.service.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface AuthorMapper {
    AuthorDtoResponse modelToDto(AuthorModel author);


    @Mappings(value = {
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "lastUpdateDate", ignore = true)
    })
    AuthorModel dtoToModel(AuthorDtoRequest author);



    @Mappings(value = {
            @Mapping(target="id", source="id"),
            @Mapping(target="createDate", ignore=true),
            @Mapping(target="lastUpdateDate", ignore=true)
    })
    AuthorModel dtoToModelUpdate(AuthorDtoRequest author);

}
