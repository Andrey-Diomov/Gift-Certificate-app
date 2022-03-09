package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.ParametersCertificateQueryDTO;
import com.epam.esm.dto.PatchCertificateDTO;
import com.epam.esm.exception.WrongDataException;
import com.epam.esm.exception.WrongIdException;
import com.epam.esm.exception.WrongPageNumberException;
import com.epam.esm.exception.WrongPageSizeException;
import com.epam.esm.hateoas.CertificateAssembler;
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.ParseUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * RestController CertificateController
 * support CRUD operation
 *
 * @author Andrey Diomov
 * @version 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/certificates")
public class CertificateController {
    private final CertificateService certificateService;
    private final CertificateAssembler certificateAssembler;

    /**
     * Gets a list of certificates by  the provided parameters.
     * <p>
     * The following parameters are expected :
     * 1) List<String> tags
     * 2) name(certificate)
     * 3) description
     * 4) sort(ASC/DESC; ASC by default)
     * Unknown parameters will be ignored.
     *
     * @return a list of certificates which match the provided parameters.
     * If there are no provided parameters then return a list of all certificates.
     */

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CertificateDTO> get(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "tagName", required = false) List<String> tags,
            @RequestParam(value = "page_number", required = false, defaultValue = "1")
            @Min(1) @Max(Integer.MAX_VALUE) String number,
            @RequestParam(value = "page_size", required = false, defaultValue = "5")
            @Min(3) @Max(Integer.MAX_VALUE) String size) {

        int pageNumber = ParseUtils.parsePageNumber(number);
        int pageSize = ParseUtils.parsePageSize(size);

        if (pageNumber < 1) {
            throw new WrongPageNumberException(pageNumber);
        }

        if (pageSize < 1) {
            throw new WrongPageSizeException(pageSize);
        }

        ParametersCertificateQueryDTO parametersDTO = ParametersCertificateQueryDTO.builder()
                .name(name)
                .description(description)
                .sort(sort)
                .tags(tags)
                .build();

        return certificateAssembler.addLink(certificateService.get(parametersDTO, pageNumber, pageSize));
    }

    /**
     * Creates new certificate by received data.
     *
     * @param certificateDTO, the received information validated and then mapped to the corresponding DTO,
     *                        including tags.
     *                        If new tags are passed  – they should be created in the DB.
     * @return certificate with field id, assigned to the certificate in the database.
     * @throws WrongDataException, If any problems occur during validation.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CertificateDTO save(@RequestBody @Valid CertificateDTO certificateDTO,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new WrongDataException(bindingResult);
        }
        return certificateAssembler.addLink(certificateService.create(certificateDTO));
    }

    /**
     * Updates only fields, that pass in request, others should not be updated.
     *
     * @param pathId of certificate.The request body contains fields for updating.
     * @return Certificate with all fields. The fields received in the request have been updated.
     * @throws WrongDataException, If any problems occur during validation.
     *                             WrongIdException, If the received id is negative.
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDTO patch(@PathVariable("id") String pathId,
                                @RequestBody @Valid PatchCertificateDTO patchCertificateDTO,
                                BindingResult bindingResult) {
        long id = ParseUtils.parseId(pathId);
        if (id < 1) {
            throw new WrongIdException(id);
        }

        if (bindingResult.hasErrors()) {
            throw new WrongDataException(bindingResult);
        }
        return certificateAssembler.addLink(certificateService.patch(patchCertificateDTO, id));
    }

    /**
     * @param pathId of the certificate we want to get.
     * @return certificate
     * @throws WrongIdException, If the received id is negative.
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDTO getById(@PathVariable("id") String pathId) {
        long id = ParseUtils.parseId(pathId);
        if (id < 1) {
            throw new WrongIdException(id);
        }
        return certificateAssembler.addLink(certificateService.get(id));
    }

    /**
     * @param pathId of the certificate we want to delete.
     * @throws WrongIdException, If the received id is negative.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") String pathId) {
        long id = ParseUtils.parseId(pathId);
        if (id < 1) {
            throw new WrongIdException(id);
        }
        certificateService.delete(id);
    }
}