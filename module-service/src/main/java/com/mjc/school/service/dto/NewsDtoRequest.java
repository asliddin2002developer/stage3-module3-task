package com.mjc.school.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class NewsDtoRequest {
        private Long id;
        private String title;
        private String content;
        private AuthorDtoResponse author;
        private Set<Long> tagIds;

        public NewsDtoRequest(String title, String content, AuthorDtoResponse author, Set<Long> tagIds){
            this.title = title;
            this.content = content;
            this.author = author;
            this.tagIds = tagIds;
        }

        @Override
        public String toString() {
            return "NewsDto{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", authorId=" + author +
                    '}';
        }
}


