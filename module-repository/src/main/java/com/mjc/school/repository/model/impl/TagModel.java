package com.mjc.school.repository.model.impl;

import com.mjc.school.repository.model.BaseEntity;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "Tag")
public class TagModel implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    Set<NewsModel> news = new HashSet<>();

    public TagModel(){

    }
    public TagModel(String name){
        this.name = name;
    }


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TagModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
