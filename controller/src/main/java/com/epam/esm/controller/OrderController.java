package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.PurchaseDTO;
import com.epam.esm.exception.WrongDataException;
import com.epam.esm.exception.WrongIdException;
import com.epam.esm.hateoas.OrderAssembler;
import com.epam.esm.security.UserSecurity;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.ParseUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderAssembler orderAssembler;
    private final UserSecurity userSecurity;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getById(@PathVariable("id") String pathId) {
        long id = ParseUtils.parseId(pathId);

        if (id < 1) {
            throw new WrongIdException(id);
        }
        return orderAssembler.addLink(orderService.get(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO create(@RequestBody @Valid PurchaseDTO purchaseDTO,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new WrongDataException(bindingResult);
        }
        ParseUtils.parseId(purchaseDTO.getCertificateId());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userAuthorityId = userSecurity.getUserId(auth);
        Optional<String> userRequestId = Optional.ofNullable(purchaseDTO.getUserId());

        if (!userSecurity.hasAdminRole(auth) || (userSecurity.hasAdminRole(auth) && userRequestId.isEmpty())) {
            purchaseDTO.setUserId(userAuthorityId);
        }

        if (userSecurity.hasAdminRole(auth) && userRequestId.isPresent()) {
            ParseUtils.parseId(purchaseDTO.getUserId());
        }

        return orderAssembler.addLink(orderService.create(purchaseDTO));
    }
}