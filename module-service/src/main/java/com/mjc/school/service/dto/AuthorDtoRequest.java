package com.mjc.school.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class AuthorDtoRequest {
    private long id;
    private String name;

    public AuthorDtoRequest(String name){
        this.name = name;
    }
    @Override
    public String toString() {
        return "AuthorDto{name='" + name + '\'' +
                '}';
    }
}
