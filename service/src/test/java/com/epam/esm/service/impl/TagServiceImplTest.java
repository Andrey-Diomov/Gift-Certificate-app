package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.impl.Tag;
import com.epam.esm.exception.TagExistsExeption;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.mapper.TagServiceMapper;
import com.epam.esm.impl.TagRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class TagServiceImplTest {
    private final TagRepositoryImpl mockTagRepository = mock(TagRepositoryImpl.class);
    private final TagServiceImpl tagService = new TagServiceImpl(mockTagRepository,
            new TagServiceMapper(new ModelMapper()));

    private Tag tag;
    private TagDTO tagDTO;

    @BeforeEach
    public void initTag() {
        tag = new Tag();
        tag.setId(1L);
        tag.setName("Sport");

        tagDTO = new TagDTO();
        tagDTO.setId(1L);
        tagDTO.setName("Sport");
    }

    @Test
    public void testGetAll() {
        int pageNumber = 1;
        int pageSize = 1;
        List<Tag> tags = List.of(tag);

        when(mockTagRepository.getAll(pageNumber, pageSize)).thenReturn(tags);
        List<TagDTO> actualTagDTOs = tagService.getAll(pageNumber, pageSize);

        verify(mockTagRepository, times(1)).getAll(pageNumber, pageSize);
        assertEquals(tags.size(), actualTagDTOs.size());
    }

    @Test
    public void testGetById() {
        long tagId = 1;

        when(mockTagRepository.get(tagId)).thenReturn(Optional.of(tag));

        TagDTO actualTag = tagService.get(tagId);

        assertEquals(tagDTO.getId(), actualTag.getId());
        assertEquals(tagDTO.getName(), actualTag.getName());
    }

    @Test
    public void testGetByIdToNonExistingId() {
        long nonExistingId = 1000;
        when(mockTagRepository.get(nonExistingId)).thenReturn(Optional.empty());
        assertThrows(TagNotFoundException.class, () -> tagService.get(nonExistingId));
    }

    @Test
    public void testCreate() {
        when(mockTagRepository.create(tag)).thenReturn(tag);

        TagDTO actualTagDTO = tagService.create(tagDTO);
        verify(mockTagRepository, times(1)).create(tag);

        assertEquals(tagDTO.getId(), actualTagDTO.getId());
        assertEquals(tagDTO.getName(), actualTagDTO.getName());
    }

    @Test
    public void testCreateToThrowException() {
        when(mockTagRepository.get(tagDTO.getName())).thenReturn(Optional.of(tag));
        assertThrows(TagExistsExeption.class, () -> tagService.create(tagDTO));
    }

    @Test
    public void testDeleteById() {
        long tagId = 1;
        when(mockTagRepository.get(tagId)).thenReturn(Optional.of(tag));
        tagService.delete(tagId);
        verify(mockTagRepository, times(1)).delete(tagId);
    }

    @Test
    public void testDeleteByNonExistingId() {
        long nonExistingId = 1000;
        when(mockTagRepository.get(nonExistingId)).thenReturn(Optional.empty());
        assertThrows(TagNotFoundException.class, () -> tagService.delete(nonExistingId));
    }
}