package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.impl.Certificate;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.mapper.CertificateServiceMapper;
import com.epam.esm.mapper.ParametersCertificateQueryServiceMapper;
import com.epam.esm.mapper.TagServiceMapper;
import com.epam.esm.impl.CertificateRepositoryImpl;
import com.epam.esm.impl.TagRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class CertificateServiceImplTest {
    private final CertificateRepositoryImpl certificateMock = mock(CertificateRepositoryImpl.class);
    private final TagRepositoryImpl tagMock = mock(TagRepositoryImpl.class);
    private final CertificateServiceImpl certificateService = new CertificateServiceImpl(tagMock,
            certificateMock, new CertificateServiceMapper(new ModelMapper()),
            new TagServiceMapper(new ModelMapper()), new ParametersCertificateQueryServiceMapper(new ModelMapper()));

    private Certificate certificate;
    private CertificateDTO certificateDTO;

    @BeforeEach
    public void initCertificate() {
        Timestamp dataTime = new Timestamp(System.currentTimeMillis());

        certificate = new Certificate();
        certificate.setId(1L);
        certificate.setName("Step by step");
        certificate.setDescription("Film about a miner life");
        certificate.setPrice(15.0);
        certificate.setDuration(10);
        certificate.setCreated(dataTime);
        certificate.setLastUpdated(dataTime);

        certificateDTO = new CertificateDTO();
        certificateDTO.setId(1L);
        certificateDTO.setName("Step by step");
        certificateDTO.setDescription("Film about a miner life");
        certificateDTO.setPrice(15.0);
        certificateDTO.setDuration(10);
        certificateDTO.setCreated(dataTime);
        certificateDTO.setLastUpdated(dataTime);
    }

    @Test
    public void testGetById() {
        long certificateId = 1;

        when(certificateMock.get(certificateId)).thenReturn(Optional.of(certificate));

        CertificateDTO actualCertificateDTO = certificateService.get(certificateId);

        assertEquals(certificateDTO.getId(), actualCertificateDTO.getId());
        assertEquals(certificateDTO.getName(), actualCertificateDTO.getName());
        assertEquals(certificateDTO.getDescription(), actualCertificateDTO.getDescription());
        assertEquals(certificateDTO.getDuration(), actualCertificateDTO.getDuration());
        assertEquals(certificateDTO.getPrice(), actualCertificateDTO.getPrice());
        assertEquals(certificateDTO.getCreated(), actualCertificateDTO.getCreated());
        assertEquals(certificateDTO.getLastUpdated(), actualCertificateDTO.getLastUpdated());
    }

    @Test
    public void testGetByIdToNonExistingId() {
        long nonExistingId = 1000;
        when(certificateMock.get(nonExistingId)).thenReturn(Optional.empty());
        assertThrows(CertificateNotFoundException.class, () -> certificateService.get(nonExistingId));
    }

    @Test
    public void testDeleteById() {
        long certificateId = 1;
        when(certificateMock.get(certificateId)).thenReturn(Optional.of(certificate));
        certificateService.delete(certificateId);
        verify(certificateMock, times(1)).delete(certificateId);
    }

    @Test
    public void testDeleteByNonExistingId() {
        long nonExistingId = 1000;
        when(certificateMock.get(nonExistingId)).thenReturn(Optional.empty());
        assertThrows(CertificateNotFoundException.class, () -> certificateService.delete(nonExistingId));
    }
}