package com.mjc.school.repository;

import com.mjc.school.repository.model.BaseEntity;

import java.util.List;

public interface BaseRepository<T extends BaseEntity<K>, P,  K> {

    List<T> readAll();

    T readById(K id);

    T create(T entity);

    T update(T entity);

    boolean deleteById(K id);

    boolean existById(K id);
    T findById(K id);
    List<T> getByParam(P param);

}
