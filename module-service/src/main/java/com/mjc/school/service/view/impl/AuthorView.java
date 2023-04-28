package com.mjc.school.service.view.impl;

import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.view.View;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorView implements View<AuthorDtoResponse, List<AuthorDtoResponse>> {

    public void display(AuthorDtoResponse author){
        System.out.println(author);
    }

    public void displayAll(List<AuthorDtoResponse> authors){
        authors.forEach(System.out::println);
    }
}
