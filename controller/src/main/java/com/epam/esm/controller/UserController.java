package com.epam.esm.controller;

import com.epam.esm.dto.*;
import com.epam.esm.exception.WrongIdException;
import com.epam.esm.exception.WrongPageNumberException;
import com.epam.esm.exception.WrongPageSizeException;
import com.epam.esm.hateoas.OrderAssembler;
import com.epam.esm.hateoas.TagAssembler;
import com.epam.esm.hateoas.UserAssembler;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ParseUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserAssembler userAssembler;
    private final OrderAssembler orderAssembler;
    private final TagAssembler tagAssembler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAll(@RequestParam(value = "page_number", required = false, defaultValue = "1")
                                @Min(1) @Max(Integer.MAX_VALUE) String number,
                                @RequestParam(value = "page_size", required = false, defaultValue = "3")
                                @Min(3) @Max(Integer.MAX_VALUE) String size) {

        int pageNumber = ParseUtils.parsePageNumber(number);
        int pageSize = ParseUtils.parsePageSize(size);

        if (pageNumber < 1) {
            throw new WrongPageNumberException(pageNumber);
        }

        if (pageSize < 1) {
            throw new WrongPageSizeException(pageSize);
        }
        return userAssembler.addLink(userService.getAll(pageNumber, pageSize));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getById(@PathVariable("id") String pathId) {
        long id = ParseUtils.parseId(pathId);

        if (id < 1) {
            throw new WrongIdException(id);
        }
        return userAssembler.addLink(userService.get(id));
    }

    @GetMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> getOrdersByUserId(@PathVariable("id") String pathId,
                                            @RequestParam(value = "page_number", required = false, defaultValue = "1")
                                            @Min(1) @Max(Integer.MAX_VALUE) String number,
                                            @RequestParam(value = "page_size", required = false, defaultValue = "1")
                                            @Min(3) @Max(Integer.MAX_VALUE) String size) {
        long id = ParseUtils.parseId(pathId);
        int pageNumber = ParseUtils.parsePageNumber(number);
        int pageSize = ParseUtils.parsePageSize(size);

        if (id < 1) {
            throw new WrongIdException(id);
        }
        return orderAssembler.addLink(userService.getOrdersByUserId(id, pageNumber, pageSize));
    }

    @GetMapping("/{id}/orders/tag")
    @ResponseStatus(HttpStatus.OK)
    public TagDTO getMostUsedTagWithHighestCostOrders(@PathVariable("id") String pathId) {
        long id = ParseUtils.parseId(pathId);

        if (id < 1) {
            throw new WrongIdException(id);
        }
        return tagAssembler.addLink(userService.getMostUsedTagWithHighestCostOrders(id));
    }
}