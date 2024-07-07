package com.fiap.burguer.driver.controller;
import com.fiap.burguer.api.CheckoutApi;
import com.fiap.burguer.core.application.usecases.OrderUseCases;
import com.fiap.burguer.driver.presenters.CheckoutPresenter;
import com.fiap.burguer.driver.dto.CheckoutResponse;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.usecases.CheckoutUseCases;
import com.fiap.burguer.core.domain.CheckOut;
import com.fiap.burguer.core.domain.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class CheckoutController implements CheckoutApi {
    CheckoutUseCases checkoutUseCases;
    OrderUseCases orderUseCases;
    public CheckoutController(CheckoutUseCases checkoutUseCases, OrderUseCases orderUseCases) {
        this.checkoutUseCases = checkoutUseCases;
        this.orderUseCases = orderUseCases;
    }

    public ResponseEntity<?> getCheckoutOrderById(int id) {
        try {
            CheckOut checkout = checkoutUseCases.findById(id);
            Order order = orderUseCases.getOrderById(checkout.getOrder().getId());
            CheckOut checkoutNew = checkoutUseCases.mapOrderToCheckout(order);
            CheckoutResponse response = CheckoutPresenter.mapCheckoutToResponse(checkoutNew);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<?> postCheckout(int id, StatusOrder statusOrder) {

        CheckOut checkoutNew = checkoutUseCases.createCheckout(id, statusOrder);
        CheckoutResponse response = CheckoutPresenter.mapCheckoutToResponse(checkoutNew);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
}
