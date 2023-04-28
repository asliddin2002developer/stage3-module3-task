package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface NewsMapper {


    @Mappings({
            @Mapping(target = "createDate", source = "createDate"),
            @Mapping(target = "lastUpdateDate", source = "lastUpdateDate"),
            @Mapping(target = "tags", source = "tags")
    })
    NewsDtoResponse modelToDto(NewsModel request);

    @Mappings({
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "lastUpdateDate", ignore = true),
            @Mapping(target = "tags", ignore = true)
    })
    NewsModel dtoToModel(NewsDtoRequest request);

}
