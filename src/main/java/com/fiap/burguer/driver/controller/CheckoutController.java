package com.fiap.burguer.driver.controller;
import com.fiap.burguer.api.CheckoutApi;
import com.fiap.burguer.core.application.usecases.GetOrderByIdUseCase;
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
    GetOrderByIdUseCase getOrderByIdUseCase;
    public CheckoutController(CheckoutUseCases checkoutUseCases, GetOrderByIdUseCase getOrderByIdUseCase) {
        this.checkoutUseCases = checkoutUseCases;
        this.getOrderByIdUseCase = getOrderByIdUseCase;
    }

    public ResponseEntity<?> getCheckoutOrderById(int id) {
        Order order = getOrderByIdUseCase.getOrderById(id);
        CheckOut checkoutNew = checkoutUseCases.mapOrderToCheckout(order);
        CheckoutResponse response = CheckoutPresenter.mapCheckoutToResponse(checkoutNew);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public ResponseEntity<?> postCheckout(int id, StatusOrder statusOrder) {

        CheckOut checkoutNew = checkoutUseCases.createCheckout(id, statusOrder);
        CheckoutResponse response = CheckoutPresenter.mapCheckoutToResponse(checkoutNew);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
