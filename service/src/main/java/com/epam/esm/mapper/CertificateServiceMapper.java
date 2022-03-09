package com.epam.esm.mapper;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.PatchCertificateDTO;
import com.epam.esm.entity.impl.Certificate;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CertificateServiceMapper {
    private final ModelMapper mapper;

    public CertificateDTO convertToDTO(Certificate certificate) {
        return mapper.map(certificate, CertificateDTO.class);
    }

    public Certificate convertToEntity(CertificateDTO certificateDTO) {
        return mapper.map(certificateDTO, Certificate.class);
    }

    public Certificate convertToEntity(PatchCertificateDTO patchCertificateDTO) {
        return mapper.map(patchCertificateDTO, Certificate.class);
    }
}