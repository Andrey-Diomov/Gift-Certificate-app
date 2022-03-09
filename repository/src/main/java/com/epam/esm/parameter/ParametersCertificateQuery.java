package com.epam.esm.parameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParametersCertificateQuery {
    private String name;
    private String description;
    private String sort;
    private List<String> tags;
}
