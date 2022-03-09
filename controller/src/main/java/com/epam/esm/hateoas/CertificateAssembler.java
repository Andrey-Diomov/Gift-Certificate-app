package com.epam.esm.hateoas;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.dto.CertificateDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@AllArgsConstructor
public class CertificateAssembler {
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String SORT = "sort";
    private static final List<String> TAG_NAME = List.of("tagName");
    private static final String PAGE_NUMBER = "page_number";
    private static final String SIZE = "page_size";

    private final TagAssembler tagAssembler;

    public CertificateDTO addLink(CertificateDTO certificateDTO) {
        certificateDTO.getTags().stream().peek(tagAssembler::addLink).collect(toList());

        certificateDTO.add(linkTo(methodOn(CertificateController.class)
                .getById(certificateDTO.getId().toString())).withSelfRel());

        return certificateDTO.add(linkTo(methodOn(CertificateController.class)
                .get(NAME, DESCRIPTION, SORT, TAG_NAME, PAGE_NUMBER, SIZE)).withSelfRel());
    }

    public List<CertificateDTO> addLink(List<CertificateDTO> certificateDTOs) {
        return certificateDTOs.stream()
                .peek(this::addLink)
                .collect(toList());
    }
}
