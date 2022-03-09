package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.TagExistsExeption;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.mapper.TagServiceMapper;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagServiceMapper mapper;

    @Transactional
    @Override
    public List<TagDTO> getAll(int pageNumber, int pageSize) {
        return tagRepository.getAll(pageNumber, pageSize)
                .stream()
                .map(mapper::convertToDTO)
                .collect(toList());
    }

    @Override
    public TagDTO get(long id) {
        return mapper.convertToDTO(tagRepository.get(id)
                .orElseThrow(() -> new TagNotFoundException(id)));
    }

    @Transactional
    @Override
    public TagDTO create(TagDTO tagDTO) {
        if (tagRepository.get(tagDTO.getName()).isPresent()) {
            throw new TagExistsExeption(tagDTO.getName());
        }
        return mapper.convertToDTO(tagRepository.create(mapper.convertToEntity(tagDTO)));
    }

    @Transactional
    @Override
    public void delete(long id) {
        tagRepository.get(id).orElseThrow(() -> new TagNotFoundException(id));
        tagRepository.delete(id);
    }
}