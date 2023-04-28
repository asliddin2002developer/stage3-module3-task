package com.mjc.school.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TagDtoRequest {
    private Long id;
    private String name;

    public TagDtoRequest(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return "TagDtoRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
