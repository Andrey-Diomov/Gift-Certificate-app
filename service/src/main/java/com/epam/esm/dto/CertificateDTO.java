package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@Data
public class CertificateDTO extends RepresentationModel<CertificateDTO> {
    private Long id;

    @Size(min = 3, max = 250)
    @NotBlank
    private String name;

    @Size(min = 3, max = 250)
    @NotBlank
    private String description;

    @NotNull
    @Positive
    private Double price;

    @Min(3)
    @Positive
    private int duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp created;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp lastUpdated;

    @Valid
    private List<TagDTO> tags;
}
