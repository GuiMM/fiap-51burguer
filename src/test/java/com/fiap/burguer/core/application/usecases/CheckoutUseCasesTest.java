package com.fiap.burguer.core.application.usecases;

import com.fiap.burguer.ApplicationTests;
import com.fiap.burguer.core.application.Exception.ImpossibleToCheckoutException;
import com.fiap.burguer.core.application.Exception.ResourceNotFoundException;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.ports.CheckOutPort;
import com.fiap.burguer.core.application.ports.IPaymentGateway;
import com.fiap.burguer.core.application.ports.OrderPort;
import com.fiap.burguer.core.domain.CheckOut;
import com.fiap.burguer.core.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CheckoutUseCasesTest {

    @Mock
    private CheckOutPort checkOutPort;

    @Mock
    private OrderPort orderPort;

    @Mock
    private IPaymentGateway paymentGateway;

    @InjectMocks
    private CheckoutUseCases checkoutUseCases;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByIdSuccessTest(){
        int id = 1;
        when(this.orderPort.findById(id)).thenReturn(new Order());
        assertNotNull(checkoutUseCases.findById(id));
    }

    @Test
    void findByIdExceptionTest(){
        int id = 1;
        when(this.orderPort.findById(id)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> checkoutUseCases.findById(id));
    }

    @Test
    void updateStatusOrderReceivedTest(){
        Order order = new Order();
        StatusOrder statusOrder = StatusOrder.RECEIVED;
        when(this.paymentGateway.processPayment(statusOrder)).thenReturn(true);
        this.checkoutUseCases.updateStatusOrder(order, statusOrder);
        verify(orderPort).save(order);
    }

    @Test
    void updateStatusOrderWaitingPaymentTest(){
        Order order = new Order();
        StatusOrder statusOrder = StatusOrder.WAITINGPAYMENT;
        when(this.paymentGateway.processPayment(statusOrder)).thenReturn(false);
        this.checkoutUseCases.updateStatusOrder(order, statusOrder);
        verify(orderPort).save(order);
    }

    @Test
    void saveTest(){
        CheckOut checkOut = new CheckOut();
        this.checkoutUseCases.save(checkOut);
        verify(checkOutPort).save(checkOut);
    }

    @Test
    void createCheckoutSuccessTest(){
        int id = 1;
        Order order = new Order();
        StatusOrder statusOrder = StatusOrder.WAITINGPAYMENT;
        order.setStatus(StatusOrder.READY);
        when(orderPort.findById(id)).thenReturn(order);
        when(checkOutPort.save(any())).thenReturn(new CheckOut());
        assertDoesNotThrow(()->this.checkoutUseCases.createCheckout(id, statusOrder));
        verify(checkOutPort).save(any(CheckOut.class));
    }

    @Test
    void createCheckoutRuntimeExceptionTest(){
        int id = 1;
        Order order = new Order();
        StatusOrder statusOrder = StatusOrder.WAITINGPAYMENT;
        order.setStatus(StatusOrder.READY);
        when(orderPort.findById(id)).thenReturn(order);
        when(checkOutPort.save(any())).thenReturn(null);
        assertThrows(RuntimeException.class,()->this.checkoutUseCases.createCheckout(id, statusOrder));
        verify(checkOutPort).save(any(CheckOut.class));
    }

    @Test
    void createCheckoutImpossibleToCheckoutExceptionTest(){
        int id = 1;
        Order order = new Order();
        StatusOrder statusOrder = StatusOrder.READY;
        order.setStatus(StatusOrder.CANCELED);
        when(orderPort.findById(id)).thenReturn(order);
        when(checkOutPort.save(any())).thenReturn(null);
        assertThrows(ImpossibleToCheckoutException.class,()->this.checkoutUseCases.createCheckout(id, statusOrder));
        verify(checkOutPort, never()).save(any(CheckOut.class));
    }

}
