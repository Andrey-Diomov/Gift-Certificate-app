package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatchCertificateDTO  {

    @Size(min = 3, max = 250)
    private String name;

    @Size(min = 3, max = 250)
    private String description;

    @Positive
    @Min(1)
    private Double price;

    @Min(3)
    @Positive
    private Integer duration;

    @Valid
    private List<TagDTO> tags;
}
