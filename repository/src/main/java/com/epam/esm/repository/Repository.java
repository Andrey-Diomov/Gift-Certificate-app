package com.epam.esm.repository;

import com.epam.esm.entity.Entity;
import java.util.Optional;

public interface Repository<T extends Entity> {

   Optional<T> get(long id);

    T create(T entity);

    void delete(long id);
}