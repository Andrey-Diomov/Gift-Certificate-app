package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;

import java.util.List;

public interface TagService extends Service<TagDTO>{
    List<TagDTO> getAll(int pageNumber, int pageSize);
}