package com.mjc.school.repository.model.impl;

import com.mjc.school.repository.model.BaseEntity;

import javax.persistence.*;


@Entity
@Table(name = "NEWSTAG")
public class NewsTag implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "news_id", nullable = false)
    private NewsModel news;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "tag_id", nullable = false)
    private TagModel tagModel;


    @Override
    public String toString() {
        return "NewsTag{" +
                "id=" + id +
                ", news=" + news +
                ", tag=" + tagModel +
                '}';
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
