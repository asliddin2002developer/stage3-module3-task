package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TagController implements BaseController<TagDtoRequest, TagDtoResponse, Long> {

    private final BaseService<TagDtoRequest, TagDtoResponse, Long> model;
    private final View<TagDtoResponse, List<TagDtoResponse>> view;
    private TagDtoResponse tag;

    @Autowired
    public TagController(BaseService<TagDtoRequest, TagDtoResponse, Long> model, View<TagDtoResponse, List<TagDtoResponse>> view) {
        this.model = model;
        this.view = view;
    }
    @Override
    public List<TagDtoResponse> readAll() {
        var tags = model.readAll();
        view.displayAll(tags);
        return tags;
    }
    @Override
    public TagDtoResponse readById(Long id) {
        tag = model.readById(id);
        view.display(tag);
        return tag;
    }

    @Override
    public TagDtoResponse create(TagDtoRequest createRequest) {
        tag = model.create(createRequest);
        view.display(tag);
        return tag;
    }

    @Override
    public TagDtoResponse update(TagDtoRequest updateRequest) {
        tag = model.update(updateRequest);
        view.display(tag);
        return tag;
    }

    @Override
    public boolean deleteById(Long id) {
        var resp = model.deleteById(id);
        System.out.println(resp);
        return resp;
    }

    @Override
    public List<TagDtoResponse> getTagsByNewsId(Long id) {
        var tags = model.getTagsByNewsId(id);
        view.displayAll(tags);
        return tags;
    }
}
