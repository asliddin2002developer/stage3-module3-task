package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.exception.NotFoundException;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.repository.model.impl.TagModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.NewsParamsRequest;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.service.mapper.NewsParamsMapper;
import com.mjc.school.service.validation.impl.NewsErrorValidator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NewsService implements BaseService<NewsDtoRequest, NewsDtoResponse, Long> {

    private final BaseRepository<NewsModel, Long> repository;
    private final BaseRepository<TagModel, Long> tagRepository;
    private final NewsErrorValidator ERROR_VALIDATOR;
    private final NewsMapper mapper;
    private final NewsParamsMapper newsParamsMapper;

    @Autowired
    public NewsService(BaseRepository<NewsModel, Long> repository,
                       BaseRepository<TagModel, Long> tagRepository,
                       NewsErrorValidator ERROR_VALIDATOR){
        this.repository = repository;
        this.tagRepository = tagRepository;
        this.ERROR_VALIDATOR = ERROR_VALIDATOR;
        this.newsParamsMapper = Mappers.getMapper(NewsParamsMapper.class);
        this.mapper = Mappers.getMapper(NewsMapper.class);
    }


    @Override
    public List<NewsDtoResponse> readAll() {
        List<NewsModel> newsList = repository.readAll();
        return newsList.stream()
                .map(mapper::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public NewsDtoResponse readById(Long id) {
        return repository
                .readById(id)
                .map(mapper::modelToDto)
                .orElseThrow(
                        () -> new NotFoundException("News NOT FOUND")
                );
    }

    @Override
    public NewsDtoResponse create(NewsDtoRequest createRequest) {
        //validate
        if (ERROR_VALIDATOR.isValidParams(createRequest)) {
            Set<Long> tagIds = createRequest.getTagIds();
            NewsModel news = mapper.dtoToModel(createRequest);
            for ( Long id : tagIds ){
                Optional<TagModel> tag = tagRepository.readById(id);
                TagModel tagModel = mapper.optionalToModel(tag);
                if (tag != null ){
                    news.addTags( tagModel );
                }
            }
            repository.create(news);
            return mapper.modelToDto(news);
        }
        throw new RuntimeException();
    }

    @Override
    public NewsDtoResponse update(NewsDtoRequest updateRequest) {
        if (ERROR_VALIDATOR.isValidParams(updateRequest)) {
            Set<Long> tagIds = updateRequest.getTagIds();
            NewsModel news = mapper.dtoToModel(updateRequest);
            System.out.println(tagIds);
            for ( Long id : tagIds ){
                Optional<TagModel> tag = tagRepository.readById(id);
                TagModel tagModel = mapper.optionalToModel(tag);

                System.out.println(tagModel);
                if (tagModel != null ){
                    news.addTags( tagModel );
                }
            }
            news = repository.update(news);
            return mapper.modelToDto(news);
        }
        throw new RuntimeException();
    }

    @Override
    public boolean deleteById(Long id) {
        if (repository.existById(id)){
            return repository.deleteById(id);
        }
        return false;
    }

    @Override
    public List<NewsDtoResponse> getNewsByParams(NewsParamsRequest params) {
        List<NewsModel> newsModels = repository.getNewsByParams(newsParamsMapper.dtoToModel(params));
        return newsModels.stream()
                .map(mapper::modelToDto)
                .collect(Collectors.toList());
    }
}
