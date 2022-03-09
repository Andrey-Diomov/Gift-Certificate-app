package com.epam.esm.mapper;

import com.epam.esm.dto.ParametersCertificateQueryDTO;
import com.epam.esm.parameter.ParametersCertificateQuery;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ParametersCertificateQueryServiceMapper {
    private final ModelMapper mapper;

    public ParametersCertificateQuery convertToEntity(ParametersCertificateQueryDTO parameters) {
        return mapper.map(parameters, ParametersCertificateQuery.class);
    }

}
