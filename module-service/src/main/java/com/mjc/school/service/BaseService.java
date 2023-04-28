package com.mjc.school.service;

import java.util.List;

public interface BaseService<T, R, P, K> {
    List<R> readAll();

    R readById(K id);

    R create(T createRequest);

    R update(T updateRequest);

    boolean deleteById(K id);

    List<R> getByParam(P p);

}
