package com.mjc.school.service.dto;

import com.mjc.school.repository.model.impl.AuthorModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class NewsDtoResponse {
        private Long id;
        private String title;
        private String content;
        private AuthorModel author;
        private LocalDateTime createDate;
        private LocalDateTime lastUpdateDate;
        private Set<TagDtoResponse> tags;


    @Override
    public String toString() {
        return "NewsDtoResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author=" + author +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", tags=" + tags +
                '}';
    }
}
