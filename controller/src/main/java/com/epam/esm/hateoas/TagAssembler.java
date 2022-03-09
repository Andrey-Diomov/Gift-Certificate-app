package com.epam.esm.hateoas;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDTO;
import org.springframework.stereotype.Component;
import java.util.List;
import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagAssembler {
    public TagDTO addLink(TagDTO tagDTO) {
        return tagDTO.add(linkTo(methodOn(TagController.class)
                .getById(tagDTO.getId().toString())).withSelfRel());
    }

    public List<TagDTO> addLink(List<TagDTO> tagDTOs) {
        return tagDTOs.stream().peek(this::addLink).collect(toList());
    }
}
