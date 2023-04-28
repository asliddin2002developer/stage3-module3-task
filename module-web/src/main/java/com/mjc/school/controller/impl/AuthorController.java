package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotations.OnDelete;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorController implements BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> {
    private final BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> model;
    private final View<AuthorDtoResponse, List<AuthorDtoResponse>> view;
    private AuthorDtoResponse author;

    @Autowired
    public AuthorController(BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> model, View<AuthorDtoResponse, List<AuthorDtoResponse>> view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public List<AuthorDtoResponse> readAll() {
        var authors = model.readAll();
        view.displayAll(authors);
        return authors;
    }

    @Override
    public AuthorDtoResponse readById(Long authorId) {
        this.author = model.readById(authorId);
        view.display(author);
        return this.author;
    }

    @Override
    public AuthorDtoResponse create(AuthorDtoRequest createRequest) {
        this.author = model.create(createRequest);
        view.display(author);
        return this.author;
    }

    @Override
    public AuthorDtoResponse update(AuthorDtoRequest updateRequest) {
        this.author = model.update(updateRequest);
        view.display(author);
        return this.author;
    }

    @OnDelete
    public boolean deleteById(Long authorId) {
        var resp = model.deleteById(authorId);
        System.out.println(resp);
        return resp;
    }

    @Override
    public AuthorDtoResponse getAuthorByNewsId(Long id) {
        var resp = model.getAuthorByNewsId(id);
        view.display(resp);
        return resp;
    }
}
