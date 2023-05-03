package com.mjc.school.repository.model.impl;


import com.mjc.school.repository.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "AUTHOR")
@EntityListeners(AuditingEntityListener.class)
public class AuthorModel implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "createDate", nullable = false)
    @CreatedDate
    private LocalDateTime createDate;

    @Column(name = "lastUpdateDate", nullable = false)
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
