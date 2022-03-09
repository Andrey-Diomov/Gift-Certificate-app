package com.epam.esm.repository;

import com.epam.esm.entity.impl.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends Repository<Tag> {
    List<Tag> getAll(int pageNumber, int pageSize);

    Optional<Tag> get(String name);

    Tag findOrCreate(Tag tag);
}