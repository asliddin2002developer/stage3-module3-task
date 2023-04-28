package com.mjc.school.service.dto;

import com.mjc.school.repository.model.impl.NewsModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class TagDtoResponse {
    private Long id;
    private String name;
    private Set<NewsModel> news;

    @Override
    public String toString() {
        return "TagDtoResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", news=" + news +
                '}';
    }
}
