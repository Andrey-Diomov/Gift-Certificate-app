package com.epam.esm.controller;

import com.epam.esm.exception.WrongPageNumberException;
import com.epam.esm.exception.WrongPageSizeException;
import com.epam.esm.hateoas.TagAssembler;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.WrongDataException;
import com.epam.esm.exception.WrongIdException;
import com.epam.esm.service.TagService;
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
 * RestController TagController
 * support CRD operation
 *
 * @author Andrey Diomov
 * @version 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;
    private final TagAssembler tagAssembler;

    /**
     * Creates new tag by received data.
     *
     * @param tagDTO, the received information validated and then mapped to the corresponding DTO.
     * @return tag with field id, assigned to the tag in the database.
     * @throws WrongDataException, If any problems occur during validation.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTO save(@RequestBody @Valid TagDTO tagDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new WrongDataException(bindingResult);
        }
        return tagAssembler.addLink(tagService.create(tagDTO));
    }

    /**
     * @return list of all tags
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDTO> getAll(@RequestParam(value = "page_number", required = false, defaultValue = "1")
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
        return tagAssembler.addLink(tagService.getAll(pageNumber, pageSize));
    }

    /**
     * @param pathId of the tag we want to get.
     * @return tag
     * @throws WrongIdException, If the received id is negative.
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDTO getById(@PathVariable("id") String pathId) {
        long id = ParseUtils.parseId(pathId);
        if (id < 1) {
            throw new WrongIdException(id);
        }
        return tagAssembler.addLink(tagService.get(id));
    }

    /**
     * @param pathId of the tag we want to delete.
     * @return
     * @throws WrongIdException, If the received id is negative.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") String pathId) {
        long id = ParseUtils.parseId(pathId);

        if (id < 1) {
            throw new WrongIdException(id);
        }
        tagService.delete(id);
    }
}