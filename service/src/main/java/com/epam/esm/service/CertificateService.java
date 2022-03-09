package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.ParametersCertificateQueryDTO;
import com.epam.esm.dto.PatchCertificateDTO;
import java.util.List;


public interface CertificateService extends Service<CertificateDTO> {

    CertificateDTO patch(PatchCertificateDTO patchCertificateDTO, long id);

    List<CertificateDTO> get(ParametersCertificateQueryDTO parameters, int pageNumber, int pageSize);
}