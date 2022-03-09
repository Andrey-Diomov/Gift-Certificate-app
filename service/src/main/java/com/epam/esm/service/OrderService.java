package com.epam.esm.service;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.PurchaseDTO;

public interface OrderService {

    OrderDTO create(PurchaseDTO purchaseDTO);

    OrderDTO get(Long id);
}