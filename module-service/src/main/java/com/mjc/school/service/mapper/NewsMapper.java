package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.repository.model.impl.TagModel;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Optional;

@Mapper
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


    @Mappings({
            @Mapping(target = "id", expression = "java(tagModelOptional.isPresent() ? tagModelOptional.get().getId() : null)"),
            @Mapping(target = "name", expression = "java(tagModelOptional.isPresent() ? tagModelOptional.get().getName() : null)"),
            @Mapping(target = "news",expression = "java(tagModelOptional.isPresent() ? tagModelOptional.get().getNews() : null)")
    })
    TagModel optionalToModel(Optional<TagModel> tagModelOptional);

}
