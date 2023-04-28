package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.exception.NotFoundException;
import com.mjc.school.repository.model.impl.TagModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.mapper.TagMapper;
import com.mjc.school.service.validation.Validator;
import com.mjc.school.service.validation.impl.TagErrorValidator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService implements BaseService<TagDtoRequest, TagDtoResponse, Long> {
    private final BaseRepository<TagModel, Long> repository;
    private final Validator<TagDtoRequest> ERROR_VALIDATOR;
    private final TagMapper mapper;

    @Autowired
    public TagService(BaseRepository<TagModel, Long> repository, TagErrorValidator ERROR_VALIDATOR) {
        this.repository = repository;
        this.ERROR_VALIDATOR = ERROR_VALIDATOR;
        this.mapper = Mappers.getMapper(TagMapper.class);
    }

    @Override
    public List<TagDtoResponse> readAll() {
        var tagList = repository.readAll();
        return tagList.stream()
                .map(mapper::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TagDtoResponse readById(Long id) {
        return mapper.modelToDto(repository.readById(id));
    }

    @Override
    public TagDtoResponse create(TagDtoRequest createRequest) {
        try{
            if (ERROR_VALIDATOR.isValidParams(createRequest)) {
                TagModel author = mapper.dtoToModel(createRequest);
                author = repository.create(author);
                return mapper.modelToDto(author);
            }
        }
        catch (NotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TagDtoResponse update(TagDtoRequest updateRequest) {
        TagModel author = repository.update(mapper.dtoToModel(updateRequest));
        return mapper.modelToDto(author);
    }

    @Override
    public boolean deleteById(Long id) {
        return repository.deleteById(id);
    }


    @Override
    public List<TagDtoResponse> getTagsByNewsId(Long id){
        List<TagModel> tags = repository.getTagsByNewsId(id);
        return tags.stream()
                .map(mapper::modelToDto)
                .collect(Collectors.toList());
    }
}
