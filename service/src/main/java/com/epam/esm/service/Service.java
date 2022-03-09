package com.epam.esm.service;

public interface Service<T> {

    T get(long id);

    T create(T entity);

    void delete(long id);
}