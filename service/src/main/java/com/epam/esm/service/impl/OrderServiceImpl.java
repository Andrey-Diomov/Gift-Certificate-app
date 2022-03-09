package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.PurchaseDTO;
import com.epam.esm.entity.impl.Certificate;
import com.epam.esm.entity.impl.Order;
import com.epam.esm.entity.impl.User;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.OrderNotFoundException;
import com.epam.esm.exception.ByIdUserNotFoundException;
import com.epam.esm.mapper.OrderServiceMapper;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CertificateRepository certificateRepository;
    private final UserRepository userRepository;
    private final OrderServiceMapper mapper;

    @Transactional
    @Override
    public OrderDTO create(PurchaseDTO purchaseDTO) {
        long userId = Long.parseLong(purchaseDTO.getUserId());
        long certificateId = Long.parseLong(purchaseDTO.getCertificateId());

        Certificate certificate = certificateRepository.get(certificateId)
                .orElseThrow(() -> new CertificateNotFoundException(certificateId));

        User user = userRepository.get(userId)
                .orElseThrow(() -> new ByIdUserNotFoundException(userId));

        Order order = Order.builder()
                .price(certificate.getPrice())
                .certificate(certificate)
                .user(user)
                .build();
        return mapper.convertToDTO(orderRepository.create((order)));
    }

    @Transactional
    @Override
    public OrderDTO get(Long id) {
        return mapper.convertToDTO(orderRepository.get(id)
                .orElseThrow(() -> new OrderNotFoundException(id)));
    }
}