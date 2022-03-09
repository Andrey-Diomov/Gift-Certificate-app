package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.ParametersCertificateQueryDTO;
import com.epam.esm.dto.PatchCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.impl.Certificate;
import com.epam.esm.entity.impl.Tag;
import com.epam.esm.exception.CertificateExistsException;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.mapper.CertificateServiceMapper;
import com.epam.esm.mapper.ParametersCertificateQueryServiceMapper;
import com.epam.esm.mapper.TagServiceMapper;
import com.epam.esm.parameter.ParametersCertificateQuery;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.CertificateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private final TagRepository tagRepository;
    private final CertificateRepository certificateRepository;
    private final CertificateServiceMapper certificateMapper;
    private final TagServiceMapper tagMapper;
    private final ParametersCertificateQueryServiceMapper queryMapper;

    @Transactional
    @Override
    public List<CertificateDTO> get(ParametersCertificateQueryDTO parametersDTO, int pageNumber, int pageSize) {
        ParametersCertificateQuery parameters = queryMapper.convertToEntity(parametersDTO);
        return certificateRepository.
                get(parameters, pageNumber, pageSize).stream()
                .map(certificateMapper::convertToDTO)
                .collect(toList());
    }

    @Override
    public CertificateDTO get(long id) {
        return certificateMapper.convertToDTO(certificateRepository.get(id)
                .orElseThrow(() -> new CertificateNotFoundException(id)));
    }

    @Transactional
    @Override
    public CertificateDTO create(CertificateDTO certificateDTO) {
        if (certificateRepository.get(certificateDTO.getName()).isPresent()) {
            throw new CertificateExistsException(certificateDTO.getName());
        }

        Certificate certificate = certificateMapper.convertToEntity(certificateDTO);
        attachTagToCertificate(certificate, certificateDTO.getTags());
        return certificateMapper.convertToDTO(certificateRepository.create(certificate));
    }

    @Transactional
    @Override
    public void delete(long id) {
        certificateRepository.get(id)
                .orElseThrow(() -> new CertificateNotFoundException(id));
        certificateRepository.delete(id);
    }

    @Transactional
    @Override
    public CertificateDTO patch(PatchCertificateDTO patchCertificateDTO, long id) {
        Certificate certificate = certificateRepository
                .get(id).orElseThrow(() -> new CertificateNotFoundException(id));

        Certificate patchCertificate = certificateMapper.convertToEntity(patchCertificateDTO);

        List<TagDTO> tagDTOs = patchCertificateDTO.getTags();
        if (tagDTOs != null) {
            attachTagToCertificate(certificate, tagDTOs);
        }
        return certificateMapper.convertToDTO(certificateRepository
                .patch(certificate, patchCertificate));
    }

    private void attachTagToCertificate(Certificate certificate, List<TagDTO> tagDTOs) {
        List<Tag> tags = new ArrayList<>();

        tagDTOs.forEach(tagDTO -> tags
                .add(tagRepository.findOrCreate(tagMapper.convertToEntity(tagDTO))));
        certificate.setTags(tags);
    }
}