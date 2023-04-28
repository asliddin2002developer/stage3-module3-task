package com.mjc.school.service.view.impl;

import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.view.View;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagView implements View<TagDtoResponse, List<TagDtoResponse>> {
    @Override
    public void display(TagDtoResponse tagDtoResponse) {
        System.out.println(tagDtoResponse);
    }

    @Override
    public void displayAll(List<TagDtoResponse> tagDtoResponses) {
        tagDtoResponses.forEach(System.out::println);
    }
}
