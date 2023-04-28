package com.mjc.school.repository.model.impl;

import com.mjc.school.repository.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "News")
public class NewsModel implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "createDate")
    @CreatedDate
    private LocalDateTime createDate;

    @Column(name = "lastUpdateDate")
    @LastModifiedDate
    private LocalDateTime lastUpdateDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "author")
    private AuthorModel author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "NewsTag",
            joinColumns = { @JoinColumn(name = "news_id")},
            inverseJoinColumns = { @JoinColumn(name = "tag_id")}
    )
    private Set<TagModel> tags = new HashSet<>();

    public NewsModel(String title, String content, AuthorModel author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public NewsModel(String title, String content, AuthorModel author, LocalDateTime createDate, LocalDateTime lastUpdateDate){
        this.title = title;
        this.content = content;
        this.author = author;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public NewsModel() {

    }

    public void addTags(TagModel tag){
        this.tags.add(tag);
    }

    @Override
    public String toString() {
        return "NewsModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", author=" + author +
                '}';
    }
}
