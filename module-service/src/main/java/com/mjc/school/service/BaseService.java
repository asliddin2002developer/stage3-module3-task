package com.mjc.school.service;

import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.NewsParamsRequest;
import com.mjc.school.service.dto.TagDtoResponse;

import java.util.List;

public interface BaseService<T, R, K> {
    List<R> readAll();

    R readById(K id);

    R create(T createRequest);

    R update(T updateRequest);

    boolean deleteById(K id);

    default AuthorDtoResponse getAuthorByNewsId(Long newsId){
        // Default implementation that throws an UnsupportedOperationException
        throw new UnsupportedOperationException("Method not implemented");
    }
    default List<TagDtoResponse> getTagsByNewsId(Long newsId){
        // Default implementation that throws an UnsupportedOperationException
        throw new UnsupportedOperationException("Method not implemented");
    }
    default List<NewsDtoResponse> getNewsByParams(NewsParamsRequest params) {
        // Default implementation that throws an UnsupportedOperationException
        throw new UnsupportedOperationException("Method not implemented");
    }

}
