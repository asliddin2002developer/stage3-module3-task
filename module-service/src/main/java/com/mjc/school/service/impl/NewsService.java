package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.impl.TagModel;
import com.mjc.school.repository.utils.NewsParams;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.service.validation.impl.NewsErrorValidator;

import org.mapstruct.factory.Mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NewsService implements BaseService<NewsDtoRequest, NewsDtoResponse, NewsParams, Long> {

    private final BaseRepository<NewsModel, NewsParams, Long> repository;
    private final BaseRepository<TagModel, Long, Long> tagRepo;
    private final NewsErrorValidator ERROR_VALIDATOR;
    private final NewsMapper mapper;

    @Autowired
    public NewsService(BaseRepository<NewsModel, NewsParams, Long> repository,
                       BaseRepository<TagModel, Long, Long> tagRepo,
                       NewsErrorValidator ERROR_VALIDATOR){
        this.repository = repository;
        this.tagRepo = tagRepo;
        this.ERROR_VALIDATOR = ERROR_VALIDATOR;
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
        NewsModel newsModel = repository.findById(id);
        return mapper.modelToDto(newsModel);
    }

    @Override
    public NewsDtoResponse create(NewsDtoRequest createRequest) {
        //validate
        if (ERROR_VALIDATOR.isValidParams(createRequest)) {
            Set<Long> tagIds = createRequest.getTagIds();
            NewsModel news = mapper.dtoToModel(createRequest);
            for ( Long id : tagIds ){
                TagModel tag = tagRepo.readById(id);
                if (tag != null ){
                    news.addTags( tag );
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
            for ( Long id : tagIds ){
                TagModel tag = tagRepo.readById(id);
                if (tag != null ){
                    news.addTags( tag );
                }
            }
            news = repository.update(mapper.dtoToModel(updateRequest));
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
    public List<NewsDtoResponse> getByParam(NewsParams param) {
        List<NewsModel> newsModels = repository.getByParam(param);
        return newsModels.stream()
                .map(mapper::modelToDto)
                .collect(Collectors.toList());
    }
}
