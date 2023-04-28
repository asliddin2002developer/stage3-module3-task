package com.mjc.school.service.validation.impl;

import com.mjc.school.repository.exception.NotFoundException;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.enums.ConstantValidators;
import com.mjc.school.service.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class AuthorErrorValidator implements Validator<AuthorDtoRequest> {

    @Override
    public boolean isValidParams(AuthorDtoRequest authorDtoRequest){
        if (authorDtoRequest.getName().length() < 3 || authorDtoRequest.getName().length() > 15){
            throw new NotFoundException(ConstantValidators.AUTHOR_NAME_LENGTH_IS_NOT_VALID.getMessage());
        }
        return true;
    }

}