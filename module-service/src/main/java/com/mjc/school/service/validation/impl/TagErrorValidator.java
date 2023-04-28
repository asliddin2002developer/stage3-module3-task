package com.mjc.school.service.validation.impl;

import com.mjc.school.repository.exception.NotFoundException;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.enums.ConstantValidators;
import com.mjc.school.service.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class TagErrorValidator implements Validator<TagDtoRequest> {
    @Override
    public boolean isValidParams(TagDtoRequest request) {
        if (request.getName().length() < 3 || request.getName().length() > 15){
            throw new NotFoundException(ConstantValidators.TAG_NAME_LENGTH_IS_NOT_VALID.getMessage());
        }
        return true;
    }
}