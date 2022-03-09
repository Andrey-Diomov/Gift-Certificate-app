package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class OrderDTO  extends RepresentationModel<OrderDTO> {
    private Long id;

    @Positive
    private Double price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp created;

   @Valid
    private CertificateDTO certificate;
}