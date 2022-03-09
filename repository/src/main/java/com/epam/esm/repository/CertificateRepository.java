package com.epam.esm.repository;

import com.epam.esm.entity.impl.Certificate;
import com.epam.esm.parameter.ParametersCertificateQuery;
import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends Repository<Certificate> {
    Certificate patch(Certificate certificate, Certificate patch);

    Optional<Certificate> get(String name);

    List<Certificate> get(ParametersCertificateQuery parameters, int pageNumber, int pageSize);
}
