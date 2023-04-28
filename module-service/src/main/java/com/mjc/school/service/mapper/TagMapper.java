package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.impl.TagModel;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TagMapper {
    TagDtoResponse modelToDto(TagModel entity);

    @Mapping(target = "news", ignore = true)
    TagModel dtoToModel(TagDtoRequest request);
}
