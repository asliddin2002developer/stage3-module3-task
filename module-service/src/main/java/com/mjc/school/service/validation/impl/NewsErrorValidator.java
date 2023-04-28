package com.mjc.school.service.validation.impl;

import com.mjc.school.repository.exception.NotFoundException;
import com.mjc.school.repository.exception.ValidatorException;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.enums.ConstantValidators;
import com.mjc.school.service.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class NewsErrorValidator implements Validator<NewsDtoRequest> {


    @Override
    public boolean isValidParams(NewsDtoRequest request){
        String title = request.getTitle();
        String content = request.getContent();
        AuthorDtoResponse author = request.getAuthor();


        if (title.length() < 5 || request.getTitle().length() >= 30) {
            throw new ValidatorException(ConstantValidators.TITLE_LENGTH_VALIDATOR.getMessage());
        } else if (content.length() < 5 || request.getContent().length() >= 255) {
            throw new ValidatorException(ConstantValidators.CONTENT_LENGTH_VALIDATOR.getMessage());
        }else if (author.getId() == null) {
            throw new NotFoundException(ConstantValidators.AUTHOR_NOT_FOUND_VALIDATOR.getMessage());
        }
        return true;
    }

}
