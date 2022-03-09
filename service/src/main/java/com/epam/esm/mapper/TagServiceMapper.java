package com.epam.esm.mapper;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.impl.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TagServiceMapper {
    private final ModelMapper mapper;

    public TagDTO convertToDTO(Tag tag) {
        return mapper.map(tag, TagDTO.class);
    }

    public Tag convertToEntity(TagDTO tagDTO) {
        return mapper.map(tagDTO, Tag.class);
    }
}