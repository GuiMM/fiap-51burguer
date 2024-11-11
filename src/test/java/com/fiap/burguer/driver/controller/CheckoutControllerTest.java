package com.fiap.burguer.driver.controller;


import com.fiap.burguer.IntegrationTest;
import com.fiap.burguer.core.application.Exception.ImpossibleToCheckoutException;
import com.fiap.burguer.core.application.Exception.ResourceNotFoundException;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.usecases.CheckoutUseCases;
import com.fiap.burguer.core.application.usecases.GetOrderByIdUseCase;
import com.fiap.burguer.core.domain.CheckOut;
import com.fiap.burguer.core.domain.Order;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fiap.burguer.driver.handlers.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CheckoutControllerTest extends IntegrationTest {

    @InjectMocks
    CheckoutController checkoutController;
    @Mock
    CheckoutUseCases checkoutUseCases;
    @Mock
    GetOrderByIdUseCase getOrderByIdUseCase;

    private Order order;
    private CheckOut checkOut;

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);
        checkoutUseCases = Mockito.mock(CheckoutUseCases.class);
        getOrderByIdUseCase = Mockito.mock(GetOrderByIdUseCase.class);
        checkoutController = new CheckoutController(checkoutUseCases, getOrderByIdUseCase);
        order = new Order();
        order.setId(1);
        order.setStatus(StatusOrder.WAITINGPAYMENT);
        order.setTotalPrice(0.0);
        order.setTimeWaitingOrder(0);
        order.setDateCreated(new java.util.Date());
        order.setOrderItemsList(List.of());
        checkOut = new CheckOut();
        checkOut.setId(1);
        checkOut.setOrder(order);
        checkOut.setPaymentStatus(StatusOrder.WAITINGPAYMENT);
        checkOut.setDateCreated(new java.util.Date());

        mvc = MockMvcBuilders.standaloneSetup(checkoutController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }


    @Nested
    class SuccessCheckoutCalls {

        @Test
        public void testGetCheckoutOrderById() throws Exception {
            when(getOrderByIdUseCase.getOrderById(1, "authorizationHeader")).thenReturn(order);
            when(checkoutUseCases.mapOrderToCheckout(order)).thenReturn(checkOut);

            mvc.perform(get("/checkout/1")
                            .header("Authorization", "authorizationHeader"))
                    .andExpect(status().isOk());

        }

        @Test
        public void testPostCheckout() throws Exception {
            when(checkoutUseCases.createCheckout(1, StatusOrder.APPROVEDPAYMENT)).thenReturn(checkOut);

            mvc.perform(post("/checkout/criar/{id}/{status_order}", 1, StatusOrder.APPROVEDPAYMENT))
                    .andExpect(status().isCreated());

        }
    }
    @Nested
    class ErrorCheckoutCalls {

        @Test
        public void testGetCheckout5xx() throws Exception {
            mvc.perform(get("/checkout/invalidId"))
                    .andExpect(status().is5xxServerError());
        }
        @Test
        public void testGetCheckoutNotFound() throws Exception {
            when(getOrderByIdUseCase.getOrderById(999, "authorizationHeader")).thenThrow(new ResourceNotFoundException("Pedido nao encontrado"));

            mvc.perform(get("/checkout/999")
                    .header("Authorization", "authorizationHeader"))
                    .andExpect(status().isNotFound());
        }

        @Test
        public void testPostImpossibleToCheckoutException() throws Exception {
            when(checkoutUseCases.createCheckout(1, StatusOrder.APPROVEDPAYMENT)).thenThrow(new ImpossibleToCheckoutException("Não foi possível realizar o pagamento, pois o pedido 1 já está com o status Finalizado"));
            mvc.perform(post("/checkout/criar/{id}/{status_order}", 1, StatusOrder.APPROVEDPAYMENT))
                    .andExpect(status().is4xxClientError());
        }
    }
}
