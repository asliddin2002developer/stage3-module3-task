package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotations.CommandBody;
import com.mjc.school.controller.annotations.CommandHandler;
import com.mjc.school.controller.annotations.CommandParam;
import com.mjc.school.controller.annotations.OnDelete;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorController implements BaseController<AuthorDtoRequest, AuthorDtoResponse, Long, Long> {
    private final BaseService<AuthorDtoRequest, AuthorDtoResponse, Long, Long> model;
    private final View<AuthorDtoResponse, List<AuthorDtoResponse>> view;
    private AuthorDtoResponse author;

    @Autowired
    public AuthorController(BaseService<AuthorDtoRequest, AuthorDtoResponse, Long, Long> model, View<AuthorDtoResponse, List<AuthorDtoResponse>> view) {
        this.model = model;
        this.view = view;
    }

    @CommandHandler
    @Override
    public List<AuthorDtoResponse> readAll() {
        var authors = model.readAll();
        view.displayAll(authors);
        return authors;
    }

    @CommandHandler
    @Override
    public AuthorDtoResponse readById(@CommandParam("authorId") Long authorId) {
        this.author = model.readById(authorId);
        view.display(author);
        return this.author;
    }

    @CommandHandler
    @Override
    public AuthorDtoResponse create(@CommandBody AuthorDtoRequest createRequest) {
        this.author = model.create(createRequest);
        view.display(author);
        return this.author;
    }

    @CommandHandler
    @Override
    public AuthorDtoResponse update(@CommandBody AuthorDtoRequest updateRequest) {
        this.author = model.update(updateRequest);
        view.display(author);
        return this.author;
    }

    @CommandHandler
    @OnDelete
    public boolean deleteById(@CommandParam("authorId") Long authorId) {
        var resp = model.deleteById(authorId);
        System.out.println(resp);
        return resp;
    }

    @CommandHandler
    @Override
    public List<AuthorDtoResponse> getByParam(@CommandParam("newsId") Long id) {
        var resp = model.getByParam(id);
        System.out.println(resp);
        return resp;
    }
}
