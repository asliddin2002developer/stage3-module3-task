package com.mjc.school.repository.model.impl;

import jakarta.persistence.*;


@Entity
@Table(name = "NewsTag")
public class NewsTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "news_id")
    private NewsModel news;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "tag_id")
    private TagModel tagModel;


    @Override
    public String toString() {
        return "NewsTag{" +
                "id=" + id +
                ", news=" + news +
                ", tag=" + tagModel +
                '}';
    }
}
