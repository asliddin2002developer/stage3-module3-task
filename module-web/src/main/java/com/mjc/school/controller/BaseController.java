package com.mjc.school.controller;

import com.mjc.school.controller.annotations.CommandBody;
import com.mjc.school.controller.annotations.CommandHandler;
import com.mjc.school.controller.annotations.CommandParam;
import com.mjc.school.repository.utils.NewsParams;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.TagDtoResponse;

import java.util.List;

public interface BaseController<T, R, K> {

    @CommandHandler
    List<R> readAll();

    @CommandHandler
    R readById(@CommandParam("id") K id);

    @CommandHandler
    R create(@CommandBody T createRequest);

    @CommandHandler
    R update(@CommandBody T updateRequest);

    @CommandHandler
    boolean deleteById(@CommandParam("id") K id);

    @CommandHandler
    default AuthorDtoResponse getAuthorByNewsId(@CommandParam("newsId") Long newsId){
        // Default implementation that throws an UnsupportedOperationException
        throw new UnsupportedOperationException("Method not implemented");
    }
    @CommandHandler
    default List<TagDtoResponse> getTagsByNewsId(@CommandParam("newsId") Long newsId){
        // Default implementation that throws an UnsupportedOperationException
        throw new UnsupportedOperationException("Method not implemented");
    }
    @CommandHandler
    default List<NewsDtoResponse> getNewsByParams(@CommandBody NewsParams params) {
        // Default implementation that throws an UnsupportedOperationException
        throw new UnsupportedOperationException("Method not implemented");
    }
}
