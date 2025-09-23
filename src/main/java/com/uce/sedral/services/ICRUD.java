package com.uce.sedral.services;

import java.util.List;

public interface ICRUD<T> {
    T save(T t);

    T update(T t);

    T findById(Integer id);

    List<T> findAll();

    void delete(Integer id);
}
