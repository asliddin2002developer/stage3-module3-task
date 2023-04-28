package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotations.CommandBody;
import com.mjc.school.controller.annotations.CommandHandler;
import com.mjc.school.controller.annotations.CommandParam;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TagController implements BaseController<TagDtoRequest, TagDtoResponse, Long, Long> {

    private final BaseService<TagDtoRequest, TagDtoResponse, Long, Long> model;
    private final View<TagDtoResponse, List<TagDtoResponse>> view;
    private TagDtoResponse tag;

    @Autowired
    public TagController(BaseService<TagDtoRequest, TagDtoResponse, Long, Long> model, View<TagDtoResponse, List<TagDtoResponse>> view) {
        this.model = model;
        this.view = view;
    }

    @CommandHandler
    @Override
    public List<TagDtoResponse> readAll() {
        var tags = model.readAll();
        view.displayAll(tags);
        return tags;
    }


    @CommandHandler
    @Override
    public TagDtoResponse readById(@CommandParam("tagId") Long id) {
        tag = model.readById(id);
        view.display(tag);
        return tag;
    }


    @CommandHandler
    @Override
    public TagDtoResponse create(@CommandBody TagDtoRequest createRequest) {
        tag = model.create(createRequest);
        view.display(tag);
        return tag;
    }

    @CommandHandler
    @Override
    public TagDtoResponse update(@CommandBody TagDtoRequest updateRequest) {
        tag = model.update(updateRequest);
        view.display(tag);
        return tag;
    }

    @CommandHandler
    @Override
    public boolean deleteById(@CommandParam("tagId") Long id) {
        var resp = model.deleteById(id);
        System.out.println(resp);
        return resp;
    }


    @CommandHandler
    @Override
    public List<TagDtoResponse> getByParam(@CommandParam("newsId") Long id) {
        var tags = model.getByParam(id);
        view.displayAll(tags);
        return tags;
    }
}
