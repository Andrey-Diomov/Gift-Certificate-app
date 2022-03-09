package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.PurchaseDTO;
import com.epam.esm.entity.impl.Certificate;
import com.epam.esm.entity.impl.Order;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.OrderNotFoundException;
import com.epam.esm.exception.ByIdUserNotFoundException;
import com.epam.esm.impl.CertificateRepositoryImpl;
import com.epam.esm.impl.OrderRepositoryImpl;
import com.epam.esm.impl.UserRepositoryImpl;
import com.epam.esm.mapper.OrderServiceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceImplTest {
    private final OrderRepositoryImpl orderMock = mock(OrderRepositoryImpl.class);
    private final CertificateRepositoryImpl certificateMock = mock(CertificateRepositoryImpl.class);
    private final UserRepositoryImpl userMock = mock(UserRepositoryImpl.class);

    private final OrderServiceImpl orderService = new OrderServiceImpl(orderMock,
            certificateMock, userMock, new OrderServiceMapper(new ModelMapper()));

    private Order order;
    private OrderDTO orderDTO;

    private Certificate certificate;
    private PurchaseDTO purchaseDTO;

    @BeforeEach
    public void init() {
        order = new Order();
        order.setId(15L);
        order.setPrice(12.0);

        orderDTO = new OrderDTO();
        orderDTO.setId(15L);
        orderDTO.setPrice(12.0);

        certificate = new Certificate();
        certificate.setId(7L);
        certificate.setName("Amazing night");

        purchaseDTO = new PurchaseDTO();
        purchaseDTO.setUserId("5");
        purchaseDTO.setCertificateId("7");
    }

    @Test
    public void testGetById() {
        long orderId = 15;

        when(orderMock.get(orderId)).thenReturn(Optional.of(order));
        OrderDTO actualOrder = orderService.get(orderId);

        assertEquals(orderDTO.getId(), actualOrder.getId());
        assertEquals(orderDTO.getPrice(), actualOrder.getPrice());
    }

    @Test
    public void testGetByIdToNonExistingId() {
        long nonExistingId = 1000;
        when(orderMock.get(nonExistingId)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> orderService.get(nonExistingId));
    }

    @Test
    public void testCreateToThrowCertificateException() {
        when(certificateMock.get(purchaseDTO.getCertificateId())).thenReturn(Optional.empty());
        assertThrows(CertificateNotFoundException.class, () -> orderService.create(purchaseDTO));
    }
}
