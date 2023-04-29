package com.mjc.school.repository.model.impl;


import com.mjc.school.repository.model.BaseEntity;
import javax.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "Author")
@EntityListeners(AuditingEntityListener.class)
public class AuthorModel implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "createDate")
    @CreatedDate
    private LocalDateTime createDate;

    @Column(name = "lastUpdateDate")
    @LastModifiedDate
    private LocalDateTime lastUpdateDate;


    public AuthorModel(String name){
        this.name = name;
    }

    public AuthorModel(String name, LocalDateTime createDate, LocalDateTime lastUpdateDate){
        this.name = name;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public AuthorModel() {

    }

    @Override
    public String toString() {
        return "AuthorModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
