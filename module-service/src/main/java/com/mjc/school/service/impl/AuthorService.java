package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.exception.NotFoundException;
import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.mapper.AuthorMapper;
import com.mjc.school.service.validation.Validator;
import com.mjc.school.service.validation.impl.AuthorErrorValidator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService implements BaseService<AuthorDtoRequest, AuthorDtoResponse, Long>{
        private final BaseRepository<AuthorModel, Long> repository;
        private final Validator<AuthorDtoRequest> ERROR_VALIDATOR;
        private final AuthorMapper mapper;

        @Autowired
        public AuthorService(BaseRepository<AuthorModel, Long> repository, AuthorErrorValidator ERROR_VALIDATOR){
            this.repository = repository;
            this.ERROR_VALIDATOR = ERROR_VALIDATOR;
            this.mapper = Mappers.getMapper(AuthorMapper.class);
        }

    @Override
    public List<AuthorDtoResponse> readAll() {
        var authorList = repository.readAll();
        return authorList.stream()
                .map(mapper::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDtoResponse readById(Long id) {
        return repository
                .readById(id)
                .map(mapper::modelToDto)
                .orElseThrow(
                        () -> new NotFoundException("Author NOT FOUND")
                );

    }

    @Override
    public AuthorDtoResponse create(AuthorDtoRequest createRequest) {
        //validate
        try{
            if (ERROR_VALIDATOR.isValidParams(createRequest)) {
                AuthorModel author = mapper.dtoToModel(createRequest);
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
    public AuthorDtoResponse update(AuthorDtoRequest updateRequest) {
        AuthorModel author = repository.update(mapper.dtoToModelUpdate(updateRequest));
        return mapper.modelToDto(author);
    }
    @Override
    public boolean deleteById(Long id) {
        return repository.deleteById(id);
    }


    @Override
    public AuthorDtoResponse getAuthorByNewsId(Long id) {
        AuthorModel authorModel = repository.getAuthorByNewsId(id);
        return mapper.modelToDto(authorModel);

    }
}
